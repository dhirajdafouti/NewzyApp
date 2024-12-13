package hoods.com.newsy.features_components.search.domain.repository

import androidx.paging.PagingData
import hoods.com.newsy.features_components.search.domain.models.SearchArticle
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun fetchSearchArticles(
        query: String,
    ): Flow<PagingData<SearchArticle>>

    suspend fun updateFavouriteArticle(article: SearchArticle)
}