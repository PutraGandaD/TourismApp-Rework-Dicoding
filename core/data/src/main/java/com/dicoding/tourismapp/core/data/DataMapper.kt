package com.dicoding.tourismapp.core.data

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity
import com.dicoding.tourismapp.core.data.source.local.entity.TourismUpsert
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse
import com.dicoding.tourismapp.core.domain.model.Tourism

object DataMapper {
    fun mapResponsesForUpsert(input: List<TourismResponse>): List<TourismUpsert> {
        val tourismList = ArrayList<TourismUpsert>()
        input.map {
            val tourism = TourismUpsert(
                tourismId = it.id,
                description = it.description,
                name = it.name,
                address = it.address,
                latitude = it.latitude,
                longitude = it.longitude,
                like = it.like,
                image = it.image
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapEntitiesToDomain(input: List<TourismEntity>): List<Tourism> =
        input.map {
            Tourism(
                tourismId = it.tourismId,
                description = it.description,
                name = it.name,
                address = it.address,
                latitude = it.latitude,
                longitude = it.longitude,
                like = it.like,
                image = it.image,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Tourism) = TourismEntity(
        tourismId = input.tourismId,
        description = input.description,
        name = input.name,
        address = input.address,
        latitude = input.latitude,
        longitude = input.longitude,
        like = input.like,
        image = input.image,
        isFavorite = input.isFavorite
    )

    fun mapEntitytoDomain(data: TourismEntity) = Tourism(
        tourismId = data.tourismId,
        description = data.description,
        name = data.name,
        address = data.address,
        latitude = data.latitude,
        longitude = data.longitude,
        like = data.like,
        image = data.image,
        isFavorite = data.isFavorite
    )
}