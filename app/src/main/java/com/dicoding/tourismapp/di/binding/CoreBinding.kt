package com.dicoding.tourismapp.di.binding

import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreBinding {
    @Binds
    abstract fun bindTourismRepository(
        repository: TourismRepository
    ) : ITourismRepository
}