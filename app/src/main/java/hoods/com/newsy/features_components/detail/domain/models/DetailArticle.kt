package hoods.com.newsy.features_components.detail.domain.models

data class DetailArticle(
    val id: Int = 0,
    val content: String,
    val description: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val favourite: Boolean,
    val category: String,
    val articleMetaData: ArticleMetaData,
){
    val paragraphs:List<String> = content.split("\r\n")
}

data class ArticleMetaData(
    val author: String,
    val publishedAt: String,
    val source: String,
    val readingTime: Int,
)
