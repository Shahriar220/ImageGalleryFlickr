package com.example.imagegallery.data.dataSource

import com.example.imagegallery.data.response.FlickrResponse
import com.example.imagegallery.utils.ResourceState

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১২:৩৫ PM
 */
interface ImageDataSource {
    suspend fun getImageData(): ResourceState<FlickrResponse>
}