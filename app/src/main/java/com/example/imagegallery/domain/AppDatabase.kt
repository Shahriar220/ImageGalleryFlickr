package com.example.imagegallery.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagegallery.domain.dao.SearchQueryDao
import com.example.imagegallery.domain.model.SearchEntity

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ৪:০৯ PM
 */
@Database(entities = [SearchEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchQueryDao(): SearchQueryDao
}