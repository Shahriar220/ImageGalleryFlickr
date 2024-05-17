package com.example.imagegallery.data.service

import com.example.imagegallery.data.response.FlickrResponse
import retrofit2.http.GET

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১০:৪১ AM
 */
interface FlickrApiService {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPublicPhotos(): FlickrResponse
}