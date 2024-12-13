package hoods.com.newsy.features_components.discover.domain.repository

import androidx.paging.PagingData
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    fun fetchDiscoverArticles(
        category: String,
        country: String,
        language: String,
    ): Flow<PagingData<NewsyArticle>>

    suspend fun updateCategory(category: String)
    suspend fun getDiscoverCurrentCategory(): String
    suspend fun updateFavouriteDiscoverCategory(article: NewsyArticle)
    suspend fun getAllAvailableCategories(): List<String>
}