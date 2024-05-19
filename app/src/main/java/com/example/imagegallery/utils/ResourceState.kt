package com.example.imagegallery.utils

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ২:২৬ PM
 */

sealed class ResourceState<T> {
    data class Success<T>(val flickrResponse: T) : ResourceState<T>()
    data class Error<T>(val error: String) : ResourceState<T>()
}