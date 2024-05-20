package com.example.imagegallery.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.imagegallery.database.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ৪:০৮ PM
 */
@Dao
interface SearchQueryDao {
    @Insert
    suspend fun insertQuery(searchQuery: SearchEntity)

    @Query("SELECT * FROM search_table ORDER BY id DESC")
    fun getAllQueries(): Flow<List<SearchEntity>>

    @Query("SELECT * FROM search_table WHERE `query` = :query LIMIT 1")
    suspend fun getSearchQuery(query: String): SearchEntity?

    @Query("Delete From search_table where id=:id")
    fun deleteFromDb(id: Int)
}