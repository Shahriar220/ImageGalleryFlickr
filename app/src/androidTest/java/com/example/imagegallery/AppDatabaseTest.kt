package com.example.imagegallery

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.imagegallery.database.AppDatabase
import com.example.imagegallery.database.dao.SearchQueryDao
import com.example.imagegallery.database.entity.SearchEntity
import junit.framework.TestCase.assertEquals
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Shahriar
 * @since ১৯/৫/২৪ ১১:৪৪ PM
 */
@RunWith(AndroidJUnit4::class)
abstract class AppDatabaseTest {
    private lateinit var searchQueryDao: SearchQueryDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        searchQueryDao.getAllQueries()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun writeAndReadQueryTest() {
        val searchEntity = SearchEntity(1, "Test Query")
        searchQueryDao.insertQuery(searchEntity)

        val loaded = searchQueryDao.getSearchQuery("Test Query")
        if (loaded != null) {
            assertEquals(loaded.query, searchEntity.query)
        }
    }
}