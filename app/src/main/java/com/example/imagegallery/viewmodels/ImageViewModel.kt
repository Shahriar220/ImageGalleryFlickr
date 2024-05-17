package com.example.imagegallery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegallery.data.response.FlickrResponse
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

    init {
        getImageData()
    }

    private fun getImageData() {
        viewModelScope.launch(Dispatchers.IO) {
            val resourceState = getImageUseCase()
            _screenState.value = when (resourceState) {
                is ResourceState.Success -> FlickrResponseState.Success(resourceState.flickrResponse)
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