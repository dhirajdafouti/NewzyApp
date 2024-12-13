package hoods.com.newsy.features_presentations.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hoods.com.newsy.R
import hoods.com.newsy.features_components.core.domain.models.DomainContract
import hoods.com.newsy.utils.TestTags
import hoods.com.newsy.utils.Utils

@Composable
fun NewsyArticleItem(
    article: DomainContract,
    onClick: (DomainContract) -> Unit,
    onFavouriteChange: (DomainContract) -> Unit,
) {

    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data(article.urlToImage)
        .crossfade(true)
        .build()

    Card(
        modifier = Modifier.padding(itemPadding).clickable {
            onClick(article)
        }
            .testTag(TestTags.NewsyArticle)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imgRequest,
                placeholder = painterResource(R.drawable.news_place_holder),
                error = painterResource(R.drawable.news_place_holder),
                contentDescription = "news Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp),
            )
            Spacer(modifier = Modifier.size(itemSpacing))
            NewsyArticleDetail(
                article = article,
                onFavouriteChange = onFavouriteChange
            )
        }
    }


}


@Composable
private fun NewsyArticleDetail(
    modifier: Modifier = Modifier,
    article: DomainContract,
    onFavouriteChange: (DomainContract) -> Unit,
) {
    val favouriteIcon = if (article.favourite) Icons.Default.BookmarkAdded
    else Icons.Default.BookmarkAdd
    Column(
        modifier = modifier
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2
        )
        Spacer(Modifier.size(itemSpacing))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.source,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.size(itemSpacing - 1.dp))
                Text(
                    text = Utils.formatPublishedAtDate(article.publishedAt),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = {
                onFavouriteChange(article)
            }) {
                Icon(
                    imageVector = favouriteIcon,
                    contentDescription = "favourite Icon Btn"
                )
            }
        }
    }

}