package com.example.imagegallery.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ১১:১৬ AM
 */

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}