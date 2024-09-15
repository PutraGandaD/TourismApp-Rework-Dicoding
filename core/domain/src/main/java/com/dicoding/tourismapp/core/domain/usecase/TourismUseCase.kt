package com.dicoding.tourismapp.core.domain.usecase

import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TourismUseCase {
    suspend fun getAllTourism(): Flow<Resource<List<Tourism>>>
    suspend fun getFavoriteTourism(): Flow<List<Tourism>>
    suspend fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}