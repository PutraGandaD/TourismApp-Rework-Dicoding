package com.dicoding.tourismapp.core.domain.usecase

import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TourismInteractor @Inject constructor(private val tourismRepository: ITourismRepository): TourismUseCase {

    override suspend fun getAllTourism() = tourismRepository.getAllTourism()

    override suspend fun getFavoriteTourism() = tourismRepository.getFavoriteTourism()

    override suspend fun setFavoriteTourism(tourism: Tourism, state: Boolean) =
        tourismRepository.setFavoriteTourism(tourism, state)

    override suspend fun getTourismById(id: String): Flow<Tourism> = tourismRepository.getTourismById(id)
}
