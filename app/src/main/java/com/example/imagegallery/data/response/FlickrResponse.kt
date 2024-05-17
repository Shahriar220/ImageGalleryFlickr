package com.example.imagegallery.data.response

import com.google.gson.annotations.SerializedName

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১০:৩৩ AM
 */
data class FlickrResponse(
    val title: String?,
    val lint: String?,
    val description: String?,
    val modified: String?,
    val items: List<Item>?
)

data class Item(
    val title: String?,
    val lint: String?,
    val media: Media?,
    @SerializedName("date_taken") val dateTaken: String?,
    val description: String?,
    val published: String?,
    val author: String?,
    @SerializedName("author_id") val authorId: String?,
    val tags: String?
)

data class Media(
    val m: String
)
