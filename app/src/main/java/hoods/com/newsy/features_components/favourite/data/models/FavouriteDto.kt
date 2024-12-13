package hoods.com.newsy.features_components.favourite.data.models

import androidx.room.ColumnInfo
import hoods.com.newsy.features_components.core.data.local.models.LocalContractDto

data class FavouriteDto(
    override val id: Int,
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
    override val favourite: Boolean,
    override val category: String,
    override var page: Int,
) : LocalContractDto()
