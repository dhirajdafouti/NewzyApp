package hoods.com.newsy.features_components.search.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.search.data.local.models.SearchDto
import hoods.com.newsy.features_components.search.data.local.models.SearchRemoteKey
import hoods.com.newsy.features_components.search.data.remote.SearchApi
import hoods.com.newsy.features_components.search.data.remote.models.toSearchDto
import hoods.com.newsy.utils.K
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val api: SearchApi,
    private val database: NewsyArticleDatabase,
    private val query: String,
) : RemoteMediator<Int, SearchDto>() {

    override suspend fun initialize(): InitializeAction {
      return  InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchDto>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prev = remoteKey?.prevKey
                prev ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
            }
        }

        return try {
            val searchResponse = api.fetchSearchArticle(query, page)
            val searchArticle = searchResponse.articles
            val endOfPaginationReached = searchArticle.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.apply {
                        searchKeyDao().clearRemoteKeys()
                        searchArticleDao().removeAllSearchArticle()
                    }
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = searchArticle.map {
                    SearchRemoteKey(
                        articleId = it.url,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        currentPage = page
                    )
                }
                database.apply {
                    searchKeyDao().insertAll(remoteKeys)
                    searchArticleDao().insertAllSearchArticles(
                        articles = searchArticle.map {
                            it.toSearchDto(page, K.SEARCH_CATEGORY)
                        }
                    )
                }
            }



            MediatorResult.Success(endOfPaginationReached)
        } catch (error: IOException) {
            MediatorResult.Error(error)
        } catch (error: HttpException) {
            MediatorResult.Error(error)
        }

    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SearchDto>,
    ): SearchRemoteKey? {
        return state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            database.searchKeyDao().getRemoteKeyArticleById(article.url)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SearchDto>,
    ): SearchRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                database.searchKeyDao().getRemoteKeyArticleById(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SearchDto>,
    ): SearchRemoteKey? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            database.searchKeyDao().getRemoteKeyArticleById(article.url)
        }
    }
}