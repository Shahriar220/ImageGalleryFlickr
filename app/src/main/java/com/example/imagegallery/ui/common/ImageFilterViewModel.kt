package com.example.imagegallery.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagegallery.domain.model.SearchEntity
import com.example.imagegallery.domain.usecase.RoomDbUseCase
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
    private val roomDbUseCase: RoomDbUseCase
) : ViewModel() {


}