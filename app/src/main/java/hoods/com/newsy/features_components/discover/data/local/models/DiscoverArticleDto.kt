package hoods.com.newsy.features_components.discover.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hoods.com.newsy.features_components.core.data.local.models.LocalContractDto

@Entity(tableName = "discover_article")
data class DiscoverArticleDto(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val author: String,
    override val content: String,
    override val description: String,
    @ColumnInfo("published_at")
    override val publishedAt: String,
    override val source: String,
    override val title: String,
    override val url: String,
    @ColumnInfo("url_to_image")
    override val urlToImage: String?,
    override val favourite: Boolean = false,
    override val category: String,
    override var page: Int,

    ) : LocalContractDto()