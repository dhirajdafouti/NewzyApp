package hoods.com.newsy.features_presentations.headline

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_presentations.core.components.HeaderTitle
import hoods.com.newsy.features_presentations.core.components.NewsyArticleItem
import hoods.com.newsy.features_presentations.core.components.PaginationLoadingItem
import hoods.com.newsy.features_presentations.core.components.itemSpacing
import hoods.com.newsy.features_presentations.headline.viewmodel.HeadlineViewModel
import hoods.com.newsy.utils.TestTags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HeadlineScreen(
    headlineViewModel: HeadlineViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {
    val headlineState = headlineViewModel.headlineState
    val headlineArticles = headlineState.headlineArticles.collectAsLazyPagingItems()
    HeadlineScreen(
        articles = headlineArticles,
        onItemClick = onItemClick,
        onFavouriteChange = headlineViewModel::onFavouriteChange
    )

}


@Composable
fun HeadlineScreen(
    articles: LazyPagingItems<NewsyArticle>,
    onItemClick: (Int) -> Unit,
    onFavouriteChange: (NewsyArticle) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.testTag(TestTags.HeadlineSnackBar)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .testTag(TestTags.HeadlineLazyColumnTestTag)
                .padding(innerPadding)
        ) {
            item {
                HeaderTitle(
                    title = "Hot news",
                    icon = Icons.Default.LocalFireDepartment
                )
                Spacer(Modifier.size(itemSpacing))
            }
            items(count = articles.itemCount) { value ->
                articles[value]?.let {
                    NewsyArticleItem(
                        article = it,
                        onClick = { clickedItem ->
                            onItemClick(clickedItem.id)
                        },
                        onFavouriteChange = { article ->
                            onFavouriteChange(article as NewsyArticle)
                        }
                    )
                }
            }
            item {
                PaginationLoadingItem(
                    pagingState = articles.loadState.mediator?.append,
                    onError = { e ->
                        scope.launch {
                            snackBarHostState.showSnackbar(message = e.message ?: "unknown error")
                        }
                    },
                    onLoading = {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .wrapContentWidth(
                                    align = Alignment.CenterHorizontally
                                )
                                .testTag(TestTags.HeadlineProgressIndicator)
                        )
                    }
                )
                PaginationLoadingItem(
                    pagingState = articles.loadState.mediator?.refresh,
                    onError = { e ->
                        scope.launch {
                            snackBarHostState.showSnackbar(message = e.message ?: "unknown error")
                        }
                    },
                    onLoading = {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .wrapContentWidth(
                                    align = Alignment.CenterHorizontally
                                )
                                .testTag(TestTags.HeadlineProgressIndicator)
                        )
                    }
                )
                PaginationLoadingItem(
                    pagingState = articles.loadState.mediator?.prepend,
                    onError = { e ->
                        scope.launch {
                            snackBarHostState.showSnackbar(message = e.message ?: "unknown error")
                        }
                    },
                    onLoading = {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .wrapContentWidth(
                                    align = Alignment.CenterHorizontally
                                )
                                .testTag(TestTags.HeadlineProgressIndicator)
                        )
                    }
                )
            }
        }
    }


}












