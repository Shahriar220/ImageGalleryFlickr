package com.example.imagegallery.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagegallery.database.dao.SearchQueryDao
import com.example.imagegallery.database.entity.SearchEntity

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ৪:০৯ PM
 */
@Database(entities = [SearchEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchQueryDao(): SearchQueryDao
}