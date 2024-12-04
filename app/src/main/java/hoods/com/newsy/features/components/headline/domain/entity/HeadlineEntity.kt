package hoods.com.newsy.features.components.headline.domain.entity


data class HeadlineEntity(
    val articleDtos: List<String> = listOf(),
    val status: String = "",
    val totalResults: Int = 0,
)
