package com.example.imagegallery.domain.usecase

import com.example.imagegallery.data.repository.ImageDataRepository
import com.example.imagegallery.data.response.FlickrResponse
import com.example.imagegallery.utils.ResourceState
import javax.inject.Inject

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ৩:১৭ PM
 */

class GetImageUseCase @Inject constructor(
    private val imageDataRepository: ImageDataRepository
) {
    suspend operator fun invoke(): ResourceState<FlickrResponse> {
        return imageDataRepository.getImageData()
    }
}