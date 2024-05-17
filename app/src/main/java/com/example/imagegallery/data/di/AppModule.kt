package com.example.imagegallery.data.di

import com.example.imagegallery.constants.ConstantsValue
import com.example.imagegallery.data.dataSource.ImageDataSource
import com.example.imagegallery.data.repository.ImageDataRepository
import com.example.imagegallery.data.service.FlickrApiService
import com.example.imagegallery.data.source.ImageDataSourceServiceImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১২:১৩ PM
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val httpLogInterCepter = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogInterCepter)
        }
        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        val gson = GsonBuilder().create()

        return Retrofit.Builder()
            .baseUrl(ConstantsValue.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): FlickrApiService {
        return retrofit.create(FlickrApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesImageData(apiService: FlickrApiService): ImageDataSource {
        return ImageDataSourceServiceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesNewsDataFromRepository(imageDataSource: ImageDataSource): ImageDataRepository {
        return ImageDataRepository(imageDataSource)
    }
}