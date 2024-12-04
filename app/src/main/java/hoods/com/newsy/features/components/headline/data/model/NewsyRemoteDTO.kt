package hoods.com.newsy.features.components.headline.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsyRemoteDto(
    @SerialName("articles")
    val articleDtos: List<String> = listOf(),
    @SerialName("status")
    val status: String = "",
    @SerialName("totalResults")
    val totalResults: Int = 0,
)
