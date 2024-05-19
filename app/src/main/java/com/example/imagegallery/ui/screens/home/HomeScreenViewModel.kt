package com.example.imagegallery.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.database.dao.SearchQueryDao
import com.example.imagegallery.database.entity.SearchEntity
import com.example.imagegallery.domain.usecase.GetImageUseCase
import com.example.imagegallery.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val searchQueryDao: SearchQueryDao
) : ViewModel() {

    private val _screenState: MutableStateFlow<FlickrResponseState> =
        MutableStateFlow(FlickrResponseState.Loading)
    val screenState: StateFlow<FlickrResponseState> = _screenState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private lateinit var allItems: List<Item>

    init {
        getImageData()
    }

    fun filterItems(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchText.isNotBlank()) {
                val existingQuery =
                    searchQueryDao.getSearchQuery(searchText.isNotBlank().toString())
                if (existingQuery == null) {
                    searchQueryDao.insertQuery(SearchEntity(query = searchText))
                }
            }
        }
        _searchText.value = searchText
        val filteredItems = if (searchText.isBlank()) {
            allItems
        } else {
            allItems.filter { item ->
                item.tags?.contains(searchText, ignoreCase = true) == true
            }
        }
        _screenState.value = FlickrResponseState.Success(filteredItems)
    }

    private fun getImageData() {
        viewModelScope.launch(Dispatchers.IO) {
            val resourceState = getImageUseCase()
            _screenState.value = when (resourceState) {
                is ResourceState.Success -> {
                    allItems = resourceState.flickrResponse.items!!
                    FlickrResponseState.Success(allItems)
                }

                is ResourceState.Error -> FlickrResponseState.Error(resourceState.error)
                else -> FlickrResponseState.Error("Unknown error")
            }
        }
    }
}

sealed interface FlickrResponseState {
    object Loading : FlickrResponseState
    data class Success(val data: List<Item>?) : FlickrResponseState
    data class Error(val errorMessage: String) : FlickrResponseState
}
