package com.dicoding.tourismapp.di.module

import android.content.Context
import androidx.room.Room
import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import com.dicoding.tourismapp.core.data.source.local.room.TourismDao
import com.dicoding.tourismapp.core.data.source.local.room.TourismDatabase
import com.dicoding.tourismapp.core.data.source.remote.RemoteDataSource
import com.dicoding.tourismapp.core.data.source.remote.network.ApiService
import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import com.dicoding.tourismapp.core.utils.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://tourism-api.dicoding.dev/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideRetrofitForLaporanApi(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : TourismDatabase {
        return Room.databaseBuilder(
            context,
            TourismDatabase::class.java,
            "tourism_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database : TourismDatabase) : TourismDao {
        return database.tourismDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: TourismDao) : LocalDataSource {
        return LocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService) : RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    fun provideTourismUseCase(
        tourismRepository: TourismRepository
    ): TourismUseCase {
        return TourismInteractor(tourismRepository)
    }

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) : ConnectivityManager {
        return ConnectivityManager(context)
    }

}