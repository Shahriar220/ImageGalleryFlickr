package com.example.imagegallery.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ১০:৩৩ AM
 */
@Parcelize
data class FlickrResponse(
    val title: String?,
    val lint: String?,
    val description: String?,
    val modified: String?,
    val items: List<Item>?
) : Parcelable

@Parcelize
data class Item(
    val title: String?,
    val link: String?,
    val media: Media?,
    @SerializedName("date_taken") val dateTaken: String?,
    val description: String?,
    val published: String?,
    val author: String?,
    @SerializedName("author_id") val authorId: String?,
    val tags: String?
) : Parcelable

@Parcelize
data class Media(
    val m: String
) : Parcelable
