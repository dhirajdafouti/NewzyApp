package hoods.com.newsy.features_components.search.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("search_key_table")
data class SearchRemoteKey(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("article_id")
    val articleId: String,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createAt: Long = System.currentTimeMillis(),
)
