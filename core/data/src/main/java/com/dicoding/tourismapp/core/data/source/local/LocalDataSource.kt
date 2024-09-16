package com.dicoding.tourismapp.core.data.source.local

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity
import com.dicoding.tourismapp.core.data.source.local.entity.TourismUpsert
import com.dicoding.tourismapp.core.data.source.local.room.TourismDao

class LocalDataSource(private val tourismDao: TourismDao) {
    suspend fun getAllTourism(): List<TourismEntity> = tourismDao.getAllTourism()

    fun getFavoriteTourism() = tourismDao.getFavoriteTourism()

    suspend fun upsertTourism(tourismList: List<TourismUpsert>) = tourismDao.upsertTourism(tourismList)

    fun getTourismById(id: String) = tourismDao.getTourismById(id)

    suspend fun setFavoriteTourism(tourism: TourismEntity, newState: Boolean) {
        tourism.isFavorite = newState
        tourismDao.updateFavoriteTourism(tourism)
    }
}