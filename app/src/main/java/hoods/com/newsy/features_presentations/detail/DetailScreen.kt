package hoods.com.newsy.features_presentations.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hoods.com.newsy.features_components.detail.domain.models.DetailArticle
import hoods.com.newsy.features_presentations.detail.components.ArticleContent
import hoods.com.newsy.features_presentations.detail.components.TopAppBar
import hoods.com.newsy.features_presentations.detail.viewmodel.DetailViewModel
import hoods.com.newsy.utils.Resource

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val selectedArticleState: Resource<DetailArticle> by detailViewModel
        .detailState
        .selectedDetailArticle
        .collectAsStateWithLifecycle(initialValue = Resource.Loading())

    when (selectedArticleState) {
        is Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            DetailScreen(
                article = (selectedArticleState as Resource.Success<DetailArticle>).data,
                navigationContent = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate up",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }

        is Resource.Error -> {
            Toast.makeText(
                LocalContext.current,
                (selectedArticleState as Resource.Error<DetailArticle>).error?.message,
                Toast.LENGTH_SHORT
            ).show()
            Log.e("Detail Screen", "DetailScreen: ", (selectedArticleState as Resource.Error<DetailArticle>).error)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreen(
    article: DetailArticle,
    lazyListState: LazyListState = rememberLazyListState(),
    navigationContent: @Composable () -> Unit,
) {
    val topAppState = rememberTopAppBarState()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppState)

    Scaffold(
        topBar = {
            TopAppBar(
                title = article.articleMetaData.source,
                navigationIconContent = navigationContent,
                scrollBehavior = scrollBehaviour
            )
        }
    ) {
        ArticleContent(
            article = article,
            modifier = Modifier
                .nestedScroll(scrollBehaviour.nestedScrollConnection)
                .padding(it),
            state = lazyListState
        )
    }

}








