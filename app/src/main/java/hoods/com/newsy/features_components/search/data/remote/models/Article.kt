package hoods.com.newsy.features_components.search.data.remote.models


import hoods.com.newsy.features_components.search.data.local.models.SearchDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("author")
    val author: String = "",
    @SerialName("content")
    val content: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("publishedAt")
    val publishedAt: String = "",
    @SerialName("source")
    val source: Source = Source(),
    @SerialName("title")
    val title: String = "",
    @SerialName("url")
    val url: String = "",
    @SerialName("urlToImage")
    val urlToImage: String = "",
)

fun Article.toSearchDto(page: Int, category: String): SearchDto {
    return SearchDto(
        author = formatEmptyValue(author, "author"),
        content = formatEmptyValue(content, "content"),
        description = formatEmptyValue(description, "description"),
        publishedAt = publishedAt,
        source = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
        page = page,
        category = category,
    )

}

private fun formatEmptyValue(value: String?, default: String = ""): String {
    return value ?: "unknown $default"
}