package hoods.com.newsy.features_components

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asPagingSourceFactory
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headline.domain.repository.HeadlineRepository
import hoods.com.newsy.utils.Utils
import kotlinx.coroutines.flow.Flow

class FakeHeadlineRepo : HeadlineRepository {
    private val articles = MutableList(Utils.newsyArticles.size) { index ->
        Utils.newsyArticles[index]
    }


    override fun fetchHeadlineArticle(
        category: String,
        country: String,
        language: String
    ): Flow<PagingData<NewsyArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = articles.asPagingSourceFactory()
        ).flow
    }

    override suspend fun updateFavouriteArticle(newsyArticle: NewsyArticle) {
        val index = articles.indexOfFirst { it.id == newsyArticle.id }
        if (index != -1) {
            articles[index] = newsyArticle.copy(favourite = newsyArticle.favourite)
        }
    }
}