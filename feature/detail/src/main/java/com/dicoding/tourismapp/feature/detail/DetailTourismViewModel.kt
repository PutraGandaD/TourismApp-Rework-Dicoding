package com.dicoding.tourismapp.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailTourismViewModel(private val tourismUseCase: TourismUseCase) : ViewModel() {
    private val _detailTourism = MutableStateFlow<Tourism?>(null)
    val detailTourism : StateFlow<Tourism?> = _detailTourism

    fun setFavoriteTourism(newStatus:Boolean) = viewModelScope.launch {
        _detailTourism.value?.let {
            tourismUseCase.setFavoriteTourism(it, newStatus)
        }
    }

    fun getTourismById(id: String) = viewModelScope.launch {
        tourismUseCase.getTourismById(id).collectLatest {
            _detailTourism.value = it
        }
    }
}

