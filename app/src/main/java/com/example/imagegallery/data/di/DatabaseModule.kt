package com.example.imagegallery.data.di

import android.content.Context
import androidx.room.Room
import com.example.imagegallery.domain.AppDatabase
import com.example.imagegallery.domain.dao.SearchQueryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ৪:১১ PM
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "image_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchQueryDao(database: AppDatabase): SearchQueryDao {
        return database.searchQueryDao()
    }
}