package hoods.com.newsy.features_components.discover.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.data.remote.models.toDiscoverArticle
import hoods.com.newsy.features_components.discover.data.local.models.DiscoverArticleDto
import hoods.com.newsy.features_components.discover.data.local.models.DiscoverKeys
import hoods.com.newsy.features_components.discover.data.remote.DiscoverApi
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class DiscoverMediator(
    private val api: DiscoverApi,
    private val database: NewsyArticleDatabase,
    private val category: String = "",
    private val country: String = "",
    private val language: String = "",
) : RemoteMediator<Int, DiscoverArticleDto>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeOut = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES)
        val isCacheTimeOut = System.currentTimeMillis() -
                (database.discoverRemoteKeyDao().getCreationTime() ?: 0) > cacheTimeOut
        val allCategories = database.discoverRemoteKeyDao()
            .getAllAvailableCategories()
        val isNotCategoryAvailable = allCategories.find { it == category } == null
        return if (isNotCategoryAvailable || isCacheTimeOut) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DiscoverArticleDto>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey
                prevKey ?: return MediatorResult.Success(
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
            val discoverArticlesApiResponse = api.getDiscoverHeadlines(
                category = category,
                page = page,
                country = country,
                language = language,
                pageSize = state.config.pageSize
            )
            val discoverArticles = discoverArticlesApiResponse.articles
            val endOfPaginationReached = discoverArticles.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.discoverRemoteKeyDao().clearRemoteKey(category)
                    database.discoverArticleDao().removeAllDiscoverArticles(
                        category
                    )
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = discoverArticles.map {
                    DiscoverKeys(
                        articleId = it.url,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        currentPage = page,
                        currentCategory = category
                    )
                }
                database.discoverRemoteKeyDao().insertAllKeys(remoteKeys)
                database.discoverArticleDao().insertAllArticles(
                    list = discoverArticles.map {
                        it.toDiscoverArticle(page, category)
                    }
                )
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, DiscoverArticleDto>,
    ): DiscoverKeys? {
        return state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            database.discoverRemoteKeyDao().getRemoteKeyByArticleId(article.url)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, DiscoverArticleDto>,
    ): DiscoverKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                database.discoverRemoteKeyDao().getRemoteKeyByArticleId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, DiscoverArticleDto>,
    ): DiscoverKeys? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            database.discoverRemoteKeyDao().getRemoteKeyByArticleId(article.url)
        }
    }

}