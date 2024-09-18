package com.dicoding.tourismapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import com.dicoding.tourismapp.core.utils.ConnectivityManager
import com.dicoding.tourismapp.core.utils.Resource
import com.dicoding.tourismapp.feature.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val connectivityManager: ConnectivityManager,
    private val tourismUseCase: TourismUseCase
) : ViewModel() {
    private val _tourismData = MutableStateFlow(HomeUiState())
    val tourismData = _tourismData.asStateFlow()

    init {
        getTourismData()
    }

    fun getTourismData() = viewModelScope.launch {
        checkInternetConnection()
        tourismUseCase.getAllTourism().collect { result ->
            when(result) {
                is Resource.Success -> {
                    _tourismData.update { currentUiState ->
                        currentUiState.copy(isLoading = false, data = result.data, message = null)
                    }
                }

                is Resource.Error -> {
                    _tourismData.update { currentUiState ->
                        currentUiState.copy(isLoading = false, data = result.data, message = result.message)
                    }
                }

                is Resource.Loading -> {
                    _tourismData.update { currentUiState ->
                        currentUiState.copy(isLoading = true, data = null, message = null)
                    }
                }
            }
        }
    }

    fun messageShowed() = viewModelScope.launch {
        _tourismData.update { currentUiState ->
            currentUiState.copy(message = null)
        }
    }

    fun checkInternetConnection() = viewModelScope.launch {
        if(connectivityManager.hasInternetConnection()) {
            _tourismData.update { currentUiState ->
                currentUiState.copy(hasInternetConnection = true)
            }
        } else {
            _tourismData.update { currentUiState ->
                currentUiState.copy(hasInternetConnection = false)
            }
        }
    }

}

