package hoods.com.newsy.features.components.headline.data.mapper

import hoods.com.newsy.features.components.headline.data.model.NewsyRemoteDto
import hoods.com.newsy.features.components.headline.domain.entity.HeadlineEntity

fun NewsyRemoteDto.toCoin(): HeadlineEntity {
    return HeadlineEntity(
        articleDtos = articleDtos,
        status = status,
        totalResults = totalResults
    )
}
