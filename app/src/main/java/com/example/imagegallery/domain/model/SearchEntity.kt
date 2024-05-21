package com.example.imagegallery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Shahriar
 * @since ১৮/৫/২৪ ৪:০৬ PM
 */
@Entity(tableName = "search_table")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val query: String
)