package hoods.com.newsy.features_components.search.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchArticleRemoteDto(
    @SerialName("articles")
    val articles: List<Article> = listOf(),
    @SerialName("status")
    val status: String = "",
    @SerialName("totalResults")
    val totalResults: Int = 0
)