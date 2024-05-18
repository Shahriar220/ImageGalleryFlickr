package com.example.imagegallery.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ১২:২৩ PM
 */

fun dateTimeConverterUtil(publishedDate: String): String {
    val zonedDateTime = ZonedDateTime.parse(publishedDate)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return zonedDateTime.format(formatter)
}