package com.dicoding.tourismapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import com.dicoding.tourismapp.core.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val tourismUseCase: TourismUseCase
) : ViewModel() {
    private val _tourismData = MutableLiveData<Resource<List<Tourism>>>()
    val tourismData : LiveData<Resource<List<Tourism>>>
        get() = _tourismData

    init {
        getTourismData()
    }

    private fun getTourismData() = viewModelScope.launch {
        tourismUseCase.getAllTourism().collect {
            _tourismData.value = it
        }
    }

}

