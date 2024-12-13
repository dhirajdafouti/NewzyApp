package hoods.com.newsy.features_presentations.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hoods.com.newsy.R
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_presentations.core.components.defaultPadding
import hoods.com.newsy.features_presentations.core.components.itemPadding
import hoods.com.newsy.features_presentations.core.components.itemSpacing
import hoods.com.newsy.features_presentations.core.ui.theme.NewsyTheme
import hoods.com.newsy.utils.Utils
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlineItem(
    articles: List<NewsyArticle>,
    articleCount: Int,
    onCardClick: (NewsyArticle) -> Unit,
    onViewMoreClick: () -> Unit,
    onFavouriteChange: (NewsyArticle) -> Unit,
) {
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { articleCount }
    )
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (isDragged) {
            isAutoScrolling = false
        } else {
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < articleCount - 1) currentPage + 1 else 0
                scrollToPage(target)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(defaultPadding),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            pageSpacing = itemSpacing
        ) { page ->
            if (isAutoScrolling) {
                AnimatedContent(
                    targetState = page,
                    label = ""
                ) { index ->
                    HeadlineCard(
                        modifier = Modifier,
                        article = articles[index],
                        onCardClick = onCardClick,
                        onFavouriteChange = onFavouriteChange
                    )
                }
            } else {
                HeadlineCard(
                    modifier = Modifier,
                    article = articles[page],
                    onCardClick = onCardClick,
                    onFavouriteChange = onFavouriteChange
                )
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        TextButton(onClick = onViewMoreClick, modifier = Modifier.align(Alignment.End)) {
            Text("view more")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeadlineCard(
    modifier: Modifier = Modifier,
    article: NewsyArticle,
    onCardClick: (NewsyArticle) -> Unit,
    onFavouriteChange: (NewsyArticle) -> Unit,
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data(article.urlToImage)
        .crossfade(true)
        .build()
    val favouriteIcon = if (article.favourite) Icons.Default.BookmarkAdded
    else Icons.Default.Bookmark
    Card(
        onClick = { onCardClick(article) },
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = imgRequest,
                placeholder = painterResource(R.drawable.ideogram_2_),
                error = painterResource(R.drawable.ideogram_2_),
                contentScale = ContentScale.Crop,
                contentDescription = "news image",
                modifier = Modifier.height(150.dp)
            )

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(itemPadding)
            )

            Row {
                Column(
                    modifier = Modifier.padding(itemPadding)
                ) {
                    Text(
                        text = article.source,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = Utils.formatPublishedAtDate(article.publishedAt),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                IconButton(onClick = { onFavouriteChange(article) }) {
                    Icon(
                        imageVector = favouriteIcon,
                        contentDescription = "favourite"
                    )
                }
            }

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PrevHeadlineItem() {
    val article = listOf(
        NewsyArticle(
            author = "Rob Tornoe, Vinny Vella, Nick Vadala",
            content = "",
            description = "Authorities have sent an alert to residents in East Nantmeal Township to “stay vigilant” and lock doors as police search a new perimeter Monday night for Danelo Cavalcante.\"",
            publishedAt = "2023-09-12T04:18:45Z",
            source = "The Philadelphia Inquirer",
            title = "Danelo Cavalcante prison escape: Updates, search area, Chester County sightings - The Philadelphia Inquirer",
            url = "https://www.inquirer.com/news/pennsylvania/live/chester-county-prison-escape-danelo-cavalcante-manhunt-updates-20230911.html",
            urlToImage = "https://www.inquirer.com/resizer/SADpRY2rJr1YKuLPVANlj6BKuRM=/760x507/smart/filters:format(webp)/cloudfront-us-east-1.images.arcpublishing.com/pmn/UJ6BBPGEU5GSJJJQWFUPO3M4QI.jpg",
            id = 0,
            category = "sports",
            page = 0
        ),
        NewsyArticle(
            author = "Rob Tornoe, Vinny Vella, Nick Vadala",
            content = "",
            description = "Authorities have sent an alert to residents in East Nantmeal Township to “stay vigilant” and lock doors as police search a new perimeter Monday night for Danelo Cavalcante.\"",
            publishedAt = "2023-09-12T04:18:45Z",
            source = "The Philadelphia Inquirer",
            title = "Danelo Cavalcante prison escape: Updates, search area, Chester County sightings - The Philadelphia Inquirer",
            url = "https://www.inquirer.com/news/pennsylvania/live/chester-county-prison-escape-danelo-cavalcante-manhunt-updates-20230911.html",
            urlToImage = "https://www.inquirer.com/resizer/SADpRY2rJr1YKuLPVANlj6BKuRM=/760x507/smart/filters:format(webp)/cloudfront-us-east-1.images.arcpublishing.com/pmn/UJ6BBPGEU5GSJJJQWFUPO3M4QI.jpg",
            id = 0,
            category = "sports",
            page = 0,
            favourite = true
        ),

        )
    NewsyTheme {
        HeadlineItem(
            article, 2, {}, {}, {}
        )
    }
}



