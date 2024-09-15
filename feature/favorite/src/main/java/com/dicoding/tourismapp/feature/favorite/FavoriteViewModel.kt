package com.dicoding.tourismapp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import com.dicoding.tourismapp.core.utils.Resource
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val tourismUseCase: TourismUseCase
) : ViewModel() {
    private val _favoriteTourismData = MutableLiveData<List<Tourism>>()
    val favoriteTourismData : LiveData<List<Tourism>>
        get() = _favoriteTourismData

    init {
        getFavoriteTourismData()
    }

    private fun getFavoriteTourismData() = viewModelScope.launch {
        tourismUseCase.getFavoriteTourism().collect {
            _favoriteTourismData.value = it
        }
    }
}

