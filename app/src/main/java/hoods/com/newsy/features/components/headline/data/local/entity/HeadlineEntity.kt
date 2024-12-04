package hoods.com.newsy.features.components.headline.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "headline_table")
data class HeadlineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String,
    val content: String,
    val description: String,
    @ColumnInfo("published_at")
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    @ColumnInfo("url_to_image")
    val urlToImage: String?,
    val favourite: Boolean = false,
    val category: String,
    var page: Int,
    )
