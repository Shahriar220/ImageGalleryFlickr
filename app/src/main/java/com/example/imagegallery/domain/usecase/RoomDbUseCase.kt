package com.example.imagegallery.domain.usecase

import com.example.imagegallery.data.repository.SearchQueryRepository
import com.example.imagegallery.domain.model.SearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Shahriar
 * @since ২১/৫/২৪ ৩:১৫ PM
 */
class RoomDbUseCase @Inject constructor(
    private val searchQueryRepository: SearchQueryRepository
) {
    suspend fun getAll(): Flow<List<SearchEntity>> {
        return searchQueryRepository.getAllQueries()
    }

    suspend fun addToDb(query: String) {
        return searchQueryRepository.addToDb(query)
    }

    suspend fun deleteFromDb(id: Int) {
        return searchQueryRepository.deleteFromDb(id)
    }
}