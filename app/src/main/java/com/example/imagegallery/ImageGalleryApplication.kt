package com.example.imagegallery

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ৯:৪৭ AM
 */
@HiltAndroidApp
class ImageGalleryApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "On Create")
    }

    companion object {
        const val TAG = "Gallery Application"
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder().maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}