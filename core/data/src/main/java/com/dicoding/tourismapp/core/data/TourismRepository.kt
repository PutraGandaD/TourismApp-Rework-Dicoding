package com.dicoding.tourismapp.core.data

import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import com.dicoding.tourismapp.core.data.source.remote.RemoteDataSource
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import com.dicoding.tourismapp.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TourismRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ITourismRepository {
    override suspend fun getAllTourism(): Flow<Resource<List<Tourism>>> = flow {
        val localTourismData = localDataSource.getAllTourism()
        if(localTourismData.isEmpty()) {
            emit(Resource.Loading())
            try {
                val response = remoteDataSource.getAllTourism()
                localDataSource.upsertTourism(DataMapper.mapResponsesToEntities(response))
                emit(Resource.Success(DataMapper.mapEntitiesToDomain(localDataSource.getAllTourism())))
            } catch (t: Throwable) {
                emit(Resource.Error(t.localizedMessage.toString()))
            }
        } else {
            emit(Resource.Success(DataMapper.mapEntitiesToDomain(localDataSource.getAllTourism())))
        }
    }

    override suspend fun getFavoriteTourism(): Flow<List<Tourism>> = flow {
        emit(DataMapper.mapEntitiesToDomain(localDataSource.getFavoriteTourism()))
    }

    override suspend fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        localDataSource.setFavoriteTourism(tourismEntity, state)
    }
}

