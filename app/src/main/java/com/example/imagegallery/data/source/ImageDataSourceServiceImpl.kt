package com.example.imagegallery.data.source

import com.example.imagegallery.data.dataSource.ImageDataSource
import com.example.imagegallery.data.response.FlickrResponse
import com.example.imagegallery.data.service.FlickrApiService
import com.example.imagegallery.utils.ResourceState
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ২:৩২ PM
 */
class ImageDataSourceServiceImpl @Inject constructor(
    private val apiService: FlickrApiService
) : ImageDataSource {

    override suspend fun getImageData(): ResourceState<FlickrResponse> = toResourceState(
        makeRequest = { apiService.getPublicPhotos() },
        parseResponse = { json -> Gson().fromJson(json, FlickrResponse::class.java) }
    )

    private suspend fun <T> toResourceState(
        makeRequest: suspend (FlickrApiService) -> Response<ResponseBody>,
        parseResponse: (String) -> T
    ): ResourceState<T> {
        Gson()
        return try {
            val response: Response<ResponseBody> = makeRequest(apiService)
            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    val rawJson = responseBody.string()
                    val json = rawJson.removePrefix("jsonFlickrFeed(").removeSuffix(")")
                    val parsedData = parseResponse(json)
                    ResourceState.Success(parsedData)
                } ?: ResourceState.Error("Empty response body")
            } else {
                ResourceState.Error("Response not successful: ${response.message()}")
            }
        } catch (e: Exception) {
            ResourceState.Error("Exception occurred: ${e.message}")
        }
    }
}