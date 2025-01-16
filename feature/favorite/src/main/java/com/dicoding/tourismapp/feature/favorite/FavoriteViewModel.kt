package com.dicoding.tourismapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import com.dicoding.tourismapp.core.utils.Resource
import com.dicoding.tourismapp.feature.favorite.FavoriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val tourismUseCase: TourismUseCase
) : ViewModel() {
    private val _favoriteTourismData = MutableStateFlow(FavoriteUiState())
    val favoriteTourismData = _favoriteTourismData.asStateFlow()

    init {
        getFavoriteTourismData()
    }

    private fun getFavoriteTourismData() = viewModelScope.launch {
        tourismUseCase.getFavoriteTourism().collectLatest { result ->
            _favoriteTourismData.update { currentUiState ->
                currentUiState.copy(data = result)
            }
        }
    }
}

