package com.dicoding.tourismapp.core.data.source.local.room

import androidx.room.*
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity
import com.dicoding.tourismapp.core.data.source.local.entity.TourismUpsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TourismDao {
    /*
        One-shot operation to get all tourism data.
     */
    @Query("SELECT * FROM tourism")
    suspend fun getAllTourism(): List<TourismEntity>

    /*
        Emit favorite tourism by id to observe user add/remove tourism data
        to favorite.
     */
    @Query("SELECT * FROM tourism where isFavorite = 1")
    fun getFavoriteTourism(): Flow<List<TourismEntity>>

    /*
        Emit Tourism by id because we'll observe the isFavorite status.
        So it's not one shot operation.
     */
    @Query("SELECT * FROM tourism where tourismId = :id")
    fun getTourismById(id: String) : Flow<TourismEntity>

    @Upsert(entity = TourismEntity::class)
    suspend fun upsertTourism(tourism: List<TourismUpsert>)

    @Update
    suspend fun updateFavoriteTourism(tourism: TourismEntity)
}
