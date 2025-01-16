package com.dicoding.tourismapp.core.data

import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import com.dicoding.tourismapp.core.data.source.remote.RemoteDataSource
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import com.dicoding.tourismapp.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TourismRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ITourismRepository {
    override suspend fun getAllTourism(): Flow<Resource<List<Tourism>>> = flow {
        emit(Resource.Loading())
        try {
            val response = remoteDataSource.getAllTourism() // get latest data from api
            localDataSource.upsertTourism(DataMapper.mapResponsesForUpsert(response)) // partial upsert to local database
            emit(Resource.Success(DataMapper.mapEntitiesToDomain(localDataSource.getAllTourism()))) // SSOT of data
        } catch (t: Throwable) {
            val localData = DataMapper.mapEntitiesToDomain(localDataSource.getAllTourism()) // return local data if API Error
            emit(Resource.Error(t.localizedMessage.toString(), localData))
        }
    }

    override suspend fun getFavoriteTourism(): Flow<List<Tourism>> =
        localDataSource.getFavoriteTourism().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override suspend fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        localDataSource.setFavoriteTourism(tourismEntity, state)
    }

    override suspend fun getTourismById(id: String): Flow<Tourism> {
        return localDataSource.getTourismById(id).map {
            DataMapper.mapEntitytoDomain(it)
        }
    }
}

