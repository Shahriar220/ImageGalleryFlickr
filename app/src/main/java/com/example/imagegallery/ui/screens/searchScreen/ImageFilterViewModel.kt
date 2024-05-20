package com.example.imagegallery.ui.screens.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegallery.data.repository.SearchQueryRepository
import com.example.imagegallery.database.entity.SearchEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shahriar
 * @since ২০/৫/২৪ ৭:৩৫ PM
 */
@HiltViewModel
class ImageFilterViewModel @Inject constructor(
    private val searchQueryRepository: SearchQueryRepository
) : ViewModel() {

    private val _searchList = MutableStateFlow<List<SearchEntity>>(emptyList())
    val searchList: StateFlow<List<SearchEntity>> get() = _searchList

    init {
        getAllQueries()
    }

    private fun getAllQueries() {
        viewModelScope.launch {
            searchQueryRepository.getAllQueries().collect { searches ->
                _searchList.value = searches
            }
        }
    }

    fun addToDb(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank()) {
                val existingQuery =
                    searchQueryRepository.getSearchQuery(query.isNotBlank().toString())
                if (existingQuery == null) {
                    searchQueryRepository.insertQuery(SearchEntity(query = query))
                }
            }
        }
    }

    fun deleteQuery(id: Int) {
        viewModelScope.launch {
            searchQueryRepository.deleteFromDb(id)
        }
    }
}