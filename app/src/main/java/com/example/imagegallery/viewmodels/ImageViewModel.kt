package com.example.imagegallery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegallery.data.response.FlickrResponse
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.domain.usecase.GetImageUseCase
import com.example.imagegallery.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ৩:২১ PM
 */

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<FlickrResponseState> =
        MutableStateFlow(FlickrResponseState.Loading)
    val screenState = _screenState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private lateinit var allItems: FlickrResponse;

    init {
        getImageData()
    }

    fun filterItems(searchText: String) {
        val filteredItems = if (searchText.isBlank()) {
            allItems
        } else {
            allItems.items?.filter { item ->
                item.tags?.contains(searchText, ignoreCase = true) == true
            }
        }
        _screenState.value = FlickrResponseState.Success(allItems)
    }

    private fun getImageData() {
        viewModelScope.launch(Dispatchers.IO) {
            val resourceState = getImageUseCase()
            _screenState.value = when (resourceState) {
                is ResourceState.Success -> {
                    allItems=resourceState.flickrResponse
                    FlickrResponseState.Success(resourceState.flickrResponse)
                }

                is ResourceState.Error -> FlickrResponseState.Error(resourceState.error)
                else -> FlickrResponseState.Error("Unknown error")
            }
        }
    }
}

sealed interface FlickrResponseState {
    data object Loading : FlickrResponseState
    data class Success(val data: FlickrResponse) : FlickrResponseState
    data class Error(val errorMessage: String) : FlickrResponseState
}