package hoods.com.newsy.features_presentations.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import hoods.com.newsy.features_components.core.domain.models.DomainContract
import hoods.com.newsy.features_components.search.domain.models.SearchArticle
import hoods.com.newsy.features_presentations.core.components.NewsyArticleItem
import hoods.com.newsy.features_presentations.core.components.PaginationLoadingItem
import hoods.com.newsy.features_presentations.core.components.defaultPadding
import hoods.com.newsy.features_presentations.search.viewmodel.SearchEvents
import hoods.com.newsy.features_presentations.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    onSearchItemClick: (DomainContract) -> Unit,
) {
    val state = searchViewModel.searchState
    val searchedArticles = state.searchArticles.collectAsLazyPagingItems()

    SearchScreen(
        searchArticle = searchedArticles,
        query = state.query,
        searchHistory = state.searchHistory,
        onQueryChange = {
            searchViewModel.onSearchEvents(SearchEvents.OnSearchChange(it))
        },
        onSubmitSearch = {
            searchViewModel.onSearchEvents(SearchEvents.SubmitSearch)
        },
        onSearchHistoryChange = {
            searchViewModel.onSearchEvents(SearchEvents.SaveSearchHistory(it))
        },
        onClearSearchHistory = {
            searchViewModel.onSearchEvents(
                SearchEvents.ClearSearchHistory
            )
        },
        onFavouriteChange = {
            searchViewModel.onSearchEvents(
                SearchEvents.OnFavouriteChange(
                    it as SearchArticle
                )
            )
        },
        onNavigateUp = navigateUp,
        onSearchItemClick = onSearchItemClick
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    searchArticle: LazyPagingItems<SearchArticle>,
    query: String,
    searchHistory: List<String>,
    onQueryChange: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    onSearchHistoryChange: (String) -> Unit,
    onClearSearchHistory: () -> Unit,
    onSearchItemClick: (DomainContract) -> Unit,
    onFavouriteChange: (DomainContract) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Explore")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "navigate back",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        var active by rememberSaveable {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {
                    onSubmitSearch()
                    onSearchHistoryChange(it)
                    active = false
                },
                onActiveChange = {
                    active = it
                },
                active = active,
                placeholder = { Text("Search Articles e.g Bitcoin") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, null) },
                modifier = Modifier.padding(horizontal = defaultPadding, vertical = 24.dp)
            ) {
                searchHistory.forEach {
                    if (it.isNotEmpty()) {
                        Row(modifier = Modifier.padding(14.dp)) {
                            Icon(imageVector = Icons.Default.History, null)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(it)
                        }
                    }
                }
                Divider()
                Text(
                    modifier = Modifier.padding(14.dp)
                        .fillMaxWidth()
                        .clickable {
                            onClearSearchHistory()
                        },
                    text = "clear all history",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Divider()
            LazyColumn {
                item {
                    PaginationLoadingItem(
                        pagingState = searchArticle.loadState.mediator?.refresh,
                        onLoading = {
                            CircularProgressIndicator(
                                modifier = Modifier.fillParentMaxWidth()
                                    .wrapContentWidth(
                                        align = Alignment.CenterHorizontally
                                    )
                            )
                        },
                        onError = {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                items(count = searchArticle.itemCount) { index ->
                    searchArticle[index]?.let { article ->
                        NewsyArticleItem(
                            article = article,
                            onClick = onSearchItemClick,
                            onFavouriteChange = onFavouriteChange
                        )
                    }
                }
            }
        }
    }

}








