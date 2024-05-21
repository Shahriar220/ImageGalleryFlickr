package com.example.imagegallery.data.repository

import com.example.imagegallery.data.dataSource.ImageDataSource
import com.example.imagegallery.data.response.FlickrResponse
import com.example.imagegallery.utils.ResourceState
import javax.inject.Inject

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ২:২৫ PM
 */
class ImageDataRepository @Inject constructor(
    private val imageDataSource: ImageDataSource
) {
    suspend fun getImageData(tag:String): ResourceState<FlickrResponse> {
        return imageDataSource.getImageData(tag)
    }
}