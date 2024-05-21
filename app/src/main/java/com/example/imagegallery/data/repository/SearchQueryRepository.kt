package com.example.imagegallery.data.repository

import com.example.imagegallery.domain.dao.SearchQueryDao
import com.example.imagegallery.domain.model.SearchEntity
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

    suspend fun addToDb(query: String) {
        if (query.isNotBlank()) {
            val existingQuery =
                searchQueryDao.getSearchQuery(query.isNotBlank().toString())
            if (existingQuery == null) {
                searchQueryDao.insertQuery(SearchEntity(query = query))
            }
        }
    }

    suspend fun getSearchQuery(query: String): SearchEntity? {
        return searchQueryDao.getSearchQuery(query)
    }

    suspend fun deleteFromDb(id: Int) {
        searchQueryDao.deleteFromDb(id)
    }
}