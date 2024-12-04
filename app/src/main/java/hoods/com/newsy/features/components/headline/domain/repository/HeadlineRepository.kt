package hoods.com.newsy.features.components.headline.domain.repository

import hoods.com.newsy.core.domain.util.NetworkError
import hoods.com.newsy.core.domain.util.Result
import hoods.com.newsy.features.components.headline.domain.entity.HeadlineEntity

interface HeadlineRepository {
    suspend fun getHeadlines():Result<HeadlineEntity,NetworkError>
}
