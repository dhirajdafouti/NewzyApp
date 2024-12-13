package hoods.com.newsy.features_components.core.data.remote.models


import hoods.com.newsy.features_components.discover.data.local.models.DiscoverArticleDto
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("author")
    val author: String = "",
    @SerialName("content")
    val content: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("publishedAt")
    val publishedAt: String = "",
    @SerialName("source")
    val source: Source = Source(),
    @SerialName("title")
    val title: String = "",
    @SerialName("url")
    val url: String = "",
    @SerialName("urlToImage")
    val urlToImage: String? = null,
)

//solution to assignment
fun Article.toDiscoverArticle(page: Int, category: String): DiscoverArticleDto {
    if (page < 0) throw IndexOutOfBoundsException("Page only accept positive value,but $page was passed")
    if (category.isEmpty()) throw IllegalArgumentException("Category can not be empty")
    return DiscoverArticleDto(
        author = formatEmptyValue(author, "author"),
        content = formatEmptyValue(content, "content"),
        description = formatEmptyValue(description, "description"),
        publishedAt = formatEmptyValue(publishedAt, "date"),
        source = formatEmptyValue(source.name, "source"),
        title = formatEmptyValue(title, "title"),
        category = category,
        url = url,
        urlToImage = urlToImage,
        page = page
    )
}

fun Article.toHeadlineArticle(page: Int, category: String): HeadlineDto {
    if (category.isEmpty()) throw IllegalArgumentException("category can not be empty")
    if (page < 0) throw IndexOutOfBoundsException("page accepts only positive values,but $page was passed")
    return HeadlineDto(
        author = formatEmptyValue(author, "author"),
        content = formatEmptyValue(content, "content"),
        description = formatEmptyValue(description, "description"),
        publishedAt = formatEmptyValue(publishedAt, "date"),
        source = formatEmptyValue(source.name, "source"),
        title = formatEmptyValue(title, "title"),
        url = url,
        urlToImage = urlToImage,
        page = page,
        category = category
    )
}


private fun formatEmptyValue(value: String?, default: String = ""): String {
    if (value.isNullOrEmpty()) return "Unknown $default"
    return value
}








