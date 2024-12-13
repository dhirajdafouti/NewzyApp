package hoods.com.newsy.features_presentations.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import hoods.com.newsy.features_components.core.domain.models.DomainContract
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_presentations.core.components.HeaderTitle
import hoods.com.newsy.features_presentations.core.components.NewsyArticleItem
import hoods.com.newsy.features_presentations.core.components.PaginationLoadingItem
import hoods.com.newsy.features_presentations.core.components.itemSpacing
import hoods.com.newsy.features_presentations.home.components.DiscoverChips
import hoods.com.newsy.features_presentations.home.components.HeadlineItem
import hoods.com.newsy.features_presentations.home.components.HomeTopAppBar
import hoods.com.newsy.features_presentations.home.viewmodel.HomeState
import hoods.com.newsy.features_presentations.home.viewmodel.HomeUIEvents
import hoods.com.newsy.features_presentations.home.viewmodel.HomeViewModel
import hoods.com.newsy.utils.ArticleCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Int) -> Unit,
    onDiscoverItemClick: (id: Int) -> Unit,
    onSearchClick: () -> Unit,
    openDrawer: () -> Unit,
) {
    val homeState = viewModel.homeState
    val headlineArticles = homeState.headlineArticles.collectAsLazyPagingItems()
    val discoverArticles = homeState.discoverArticles.collectAsLazyPagingItems()
    val categories = ArticleCategory.values()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
                onSearch = onSearchClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            headlineItems(
                headlineArticles = headlineArticles,
                scope = scope,
                snackbarHostState = snackbarHostState,
                onViewMoreClick = onViewMoreClick,
                onHeadlineItemClick = onHeadlineItemClick,
                onFavouriteHeadlineChange = {
                    viewModel.onHomeUIEvents(
                        HomeUIEvents.OnHeadLineFavouriteChange(
                            article = it
                        )
                    )
                }
            )

            DiscoverItems(
                homeState = homeState,
                categories = categories.toList(),
                discoverArticles = discoverArticles,
                scope = scope,
                snackbarHostState = snackbarHostState,
                onCategoryChange = { category ->
                    viewModel.onHomeUIEvents(
                        HomeUIEvents.CategoryChange(category)
                    )
                },
                onItemClick = onDiscoverItemClick,
                onFavouriteChange = { article ->
                    viewModel.onHomeUIEvents(
                        HomeUIEvents.OnDiscoverFavouriteChange(article as NewsyArticle)
                    )
                }
            )
        }
    }
}

private fun LazyListScope.headlineItems(
    headlineArticles: LazyPagingItems<NewsyArticle>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onViewMoreClick: () -> Unit,
    onHeadlineItemClick: (id: Int) -> Unit,
    onFavouriteHeadlineChange: (NewsyArticle) -> Unit,
) {
    item {
        HeaderTitle(
            title = "Hot News",
            icon = Icons.Default.LocalFireDepartment
        )
        Spacer(modifier = Modifier.size(itemSpacing))
    }

    item {
        PaginationLoadingItem(
            pagingState = headlineArticles.loadState.mediator?.refresh,
            onError = { e ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = e.message ?: "unknown error"
                    )
                }
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            }
        )
    }

    item {
        val articleList = headlineArticles.itemSnapshotList.items
        HeadlineItem(
            articles = articleList,
            articleCount = if (articleList.isEmpty()) 0 else articleList.lastIndex,
            onCardClick = {
                onHeadlineItemClick(it.id)
            },
            onViewMoreClick = onViewMoreClick,
            onFavouriteChange = onFavouriteHeadlineChange
        )
    }
}



private fun LazyListScope.DiscoverItems(
    homeState: HomeState,
    categories: List<ArticleCategory>,
    discoverArticles: LazyPagingItems<NewsyArticle>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onItemClick: (id: Int) -> Unit,
    onCategoryChange: (ArticleCategory) -> Unit,
    onFavouriteChange: (article: DomainContract) -> Unit,
) {
    item {
        HeaderTitle(
            title = "Discover News",
            icon = Icons.Default.Newspaper
        )
        Spacer(modifier = Modifier.size(itemSpacing))
        DiscoverChips(
            selectedCategory = homeState.selectedDiscoverCategory,
            categories = categories,
            onCategoryChange = onCategoryChange
        )
    }

    item {
        PaginationLoadingItem(
            pagingState = discoverArticles.loadState.mediator?.refresh,
            onError = { e ->
                scope.launch {
                    snackbarHostState.showSnackbar(e.message ?: " unknown error")
                }
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(
                            align = Alignment.CenterHorizontally
                        )
                )
            }
        )
    }

    items(count = discoverArticles.itemCount) { index ->
        discoverArticles[index]?.let {
            NewsyArticleItem(
                article = it,
                onClick = { article ->
                    onItemClick(article.id)
                },
                onFavouriteChange = onFavouriteChange
            )
        }
    }

    item {
        PaginationLoadingItem(
            pagingState = discoverArticles.loadState.mediator?.append,
            onError = { e ->
                scope.launch {
                    snackbarHostState.showSnackbar(e.message ?: " unknown error")
                }
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(
                            align = Alignment.CenterHorizontally
                        )
                )
            }
        )
    }

}







