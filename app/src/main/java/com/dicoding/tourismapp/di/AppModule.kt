package com.dicoding.tourismapp.di

import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import com.dicoding.tourismapp.core.utils.ConnectivityManager
import com.dicoding.tourismapp.favorite.FavoriteViewModel
import com.dicoding.tourismapp.feature.detail.DetailTourismViewModel
import com.dicoding.tourismapp.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TourismUseCase> {
        TourismInteractor(
            get()
        )
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailTourismViewModel(get()) }
}

val utilsModule = module {
    factory { ConnectivityManager(get()) }
}