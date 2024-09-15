package com.dicoding.tourismapp.core.data.source.local

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity
import com.dicoding.tourismapp.core.data.source.local.room.TourismDao

class LocalDataSource(private val tourismDao: TourismDao) {
    suspend fun getAllTourism(): List<TourismEntity> = tourismDao.getAllTourism()

    suspend fun getFavoriteTourism(): List<TourismEntity> = tourismDao.getFavoriteTourism()

    suspend fun upsertTourism(tourismList: List<TourismEntity>) = tourismDao.upsertTourism(tourismList)

    suspend fun setFavoriteTourism(tourism: TourismEntity, newState: Boolean) {
        tourism.isFavorite = newState
        tourismDao.updateFavoriteTourism(tourism)
    }
}