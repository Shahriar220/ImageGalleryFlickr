package com.example.imagegallery.data.repository

import com.example.imagegallery.database.dao.SearchQueryDao
import com.example.imagegallery.database.entity.SearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Shahriar
 * @since ২০/৫/২৪ ৭:৫৭ PM
 */

class SearchQueryRepository @Inject constructor(private val searchQueryDao: SearchQueryDao) {

    fun getAllQueries(): Flow<List<SearchEntity>> {
        return searchQueryDao.getAllQueries()
    }

    suspend fun insertQuery(searchQuery: SearchEntity) {
        searchQueryDao.insertQuery(searchQuery)
    }

    suspend fun getSearchQuery(query: String): SearchEntity? {
        return searchQueryDao.getSearchQuery(query)
    }

    fun deleteFromDb(id: Int) {
        searchQueryDao.deleteFromDb(id)
    }
}