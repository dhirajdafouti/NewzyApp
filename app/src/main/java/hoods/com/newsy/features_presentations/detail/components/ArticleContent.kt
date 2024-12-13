package hoods.com.newsy.features_presentations.detail.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hoods.com.newsy.R
import hoods.com.newsy.features_components.detail.domain.models.ArticleMetaData
import hoods.com.newsy.features_components.detail.domain.models.DetailArticle
import hoods.com.newsy.features_presentations.core.components.defaultPadding
import hoods.com.newsy.features_presentations.core.components.defaultSpacing
import hoods.com.newsy.features_presentations.core.components.itemSpacing
import hoods.com.newsy.utils.Utils

@Composable
fun ArticleContent(
    article: DetailArticle,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        contentPadding = PaddingValues(defaultPadding),
        modifier = modifier,
        state = state
    ) {
        articleContentItems(article)
    }

}

fun LazyListScope.articleContentItems(article: DetailArticle) {
    item {
        ArticleImage(article)
        Spacer(modifier = Modifier.height(itemSpacing))
        Text(text = article.title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(itemSpacing))
        if (article.description.isNotEmpty()) {
            Text(text = article.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(defaultSpacing))
        }
    }
    item {
        ArticleMetaDataContent(
            articleMetaData = article.articleMetaData,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
    items(article.paragraphs) { paragraph ->
        ParagraphContent(
            paragraph
        )
    }
    item {
        val context = LocalContext.current
        TextButton(
            onClick = {
                navigateToUrl(article.url, context)
            }
        ) {
            Text("Click here to view full article")
        }
    }
}

private fun navigateToUrl(
    url: String,
    context: Context,
) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    context.startActivity(intent)
}

@Composable
private fun ArticleImage(article: DetailArticle) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data(article.urlToImage)
        .crossfade(true)
        .build()
    AsyncImage(
        model = imgRequest,
        placeholder = painterResource(R.drawable.news_place_holder),
        error = painterResource(R.drawable.news_place_holder),
        contentDescription = "news image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 300.dp)
    )
}


@Composable
private fun ArticleMetaDataContent(
    articleMetaData: ArticleMetaData,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Image(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(itemSpacing))
        Column {
            val formattedDate = Utils.formatPublishedAtDate(articleMetaData.publishedAt)
            val readingTime = "${articleMetaData.readingTime} min read"
            Text(
                text = articleMetaData.author,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 3.dp)
            )
            Text(
                text = "$formattedDate • $readingTime"
            )
        }
    }

}


@Composable
private fun ParagraphContent(
    paragraph: String,
) {
    Box(modifier = Modifier.padding(defaultPadding)) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = paragraph,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 28.sp
            )
        )
    }

}







