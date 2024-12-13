package hoods.com.newsy.features_components.favourite.domain.model

import hoods.com.newsy.features_components.core.domain.models.DomainContract

data class FavouriteArticle(
    override val id: Int,
    override val author: String,
    override val content: String,
    override val description: String,
    override val publishedAt: String,
    override val source: String,
    override val title: String,
    override val url: String,
    override val urlToImage: String?,
    override val favourite: Boolean,
    override val category: String,
    override var page: Int

):DomainContract()
