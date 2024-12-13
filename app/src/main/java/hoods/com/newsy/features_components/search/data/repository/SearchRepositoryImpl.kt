package hoods.com.newsy.features_components.search.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.search.data.local.models.SearchDto
import hoods.com.newsy.features_components.search.data.paging.SearchRemoteMediator
import hoods.com.newsy.features_components.search.data.remote.SearchApi
import hoods.com.newsy.features_components.search.domain.models.SearchArticle
import hoods.com.newsy.features_components.search.domain.repository.SearchRepository
import hoods.com.newsy.utils.K
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val api: SearchApi,
    private val database: NewsyArticleDatabase,
    private val mapper: Mapper<SearchDto, SearchArticle>,
) : SearchRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchSearchArticles(query: String): Flow<PagingData<SearchArticle>> {
        return Pager(
            PagingConfig(pageSize = K.PAGE_SIZE),
            pagingSourceFactory = {
                database.searchArticleDao().getAllSearchArticle()
            },
            remoteMediator = SearchRemoteMediator(
                api, database, query
            )
        ).flow.map { pagerDto ->
            pagerDto.map {
                mapper.toModel(it)
            }
        }
    }

    override suspend fun updateFavouriteArticle(article: SearchArticle) {
        database.searchArticleDao().updateFavouriteArticle(
            isFavourite = article.favourite,
            id = article.id
        )
    }
}