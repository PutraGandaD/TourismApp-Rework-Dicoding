package com.dicoding.tourismapp.core.data.source.local.room

import androidx.room.*
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TourismDao {
    @Query("SELECT * FROM tourism")
    suspend fun getAllTourism(): List<TourismEntity>

    @Query("SELECT * FROM tourism where isFavorite = 1")
    suspend fun getFavoriteTourism(): List<TourismEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourism(tourism: List<TourismEntity>)

    @Update
    suspend fun updateFavoriteTourism(tourism: TourismEntity)
}
