package com.example.imagegallery.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১০:৪১ AM
 */
interface FlickrApiService {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPublicPhotos(@Query("tags") tag: String): Response<ResponseBody>
}