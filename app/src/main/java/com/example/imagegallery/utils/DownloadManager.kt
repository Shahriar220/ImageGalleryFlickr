package com.example.imagegallery.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ১:১৪ PM
 */
suspend fun saveImageToGallery(context: Context, imageUrl: String): Uri? {
    val imageLoader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .build()

    val bitmap = withContext(Dispatchers.IO) {
        imageLoader.execute(request).drawable?.toBitmap()
    } ?: return null

    val filename = "${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    var imageUri: Uri? = null

    try {
        // Get the content resolver
        val resolver: ContentResolver = context.contentResolver

        // Create a ContentValues object to hold the image metadata
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }

        // Get the URI of the external content directory
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        // Insert the image into the MediaStore
        imageUri = resolver.insert(externalUri, contentValues)

        // Open an output stream to write the bitmap data to the MediaStore
        fos = imageUri?.let { resolver.openOutputStream(it) }

        // Compress and write the bitmap data to the output stream
        if (fos != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
    } catch (e: Exception) {
        // Handle exceptions if any
        e.printStackTrace()
    } finally {
        // Close the output stream
        fos?.close()
    }

    return imageUri
}
