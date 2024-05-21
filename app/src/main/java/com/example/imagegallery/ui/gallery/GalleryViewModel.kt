package com.example.imagegallery.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.domain.model.SearchEntity
import com.example.imagegallery.domain.usecase.GetImageUseCase
import com.example.imagegallery.domain.usecase.RoomDbUseCase
import com.example.imagegallery.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val getRoomDbUseCase: RoomDbUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<FlickrResponseState> =
        MutableStateFlow(FlickrResponseState.Loading)
    val screenState: StateFlow<FlickrResponseState> = _screenState.asStateFlow()

    private val _searchList = MutableStateFlow<List<SearchEntity>>(emptyList())
    val searchList: StateFlow<List<SearchEntity>> get() = _searchList

    private val _searchText = MutableStateFlow("")

    private lateinit var allItems: List<Item>

    init {
        getImageData("")
        getAllQueries()
    }

    fun filterItems(searchText: String) {
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

    fun getImageData(tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resourceState = getImageUseCase(tag)
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

    private fun getAllQueries() {
        viewModelScope.launch {
            getRoomDbUseCase.getAll().collect { searches ->
                _searchList.value = searches
            }
        }
    }

    fun addToDb(query: String) {
        viewModelScope.launch {
            getRoomDbUseCase.addToDb(query)
        }
    }

    fun deleteQuery(id: Int) {
        viewModelScope.launch {
            getRoomDbUseCase.deleteFromDb(id)
        }
    }
}

sealed interface FlickrResponseState {
    object Loading : FlickrResponseState
    data class Success(val data: List<Item>?) : FlickrResponseState
    data class Error(val errorMessage: String) : FlickrResponseState
}
