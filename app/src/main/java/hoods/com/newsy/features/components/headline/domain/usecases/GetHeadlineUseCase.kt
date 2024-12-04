package hoods.com.newsy.features.components.headline.domain.usecases

import hoods.com.newsy.core.domain.util.NetworkError
import hoods.com.newsy.core.domain.util.Result
import hoods.com.newsy.features.components.headline.domain.entity.HeadlineEntity
import hoods.com.newsy.features.components.headline.domain.repository.HeadlineRepository
import javax.inject.Inject

class GetHeadlineUseCase @Inject constructor(
    private val headlineRepository: HeadlineRepository,
) {
    suspend fun getHeadlines(): Result<HeadlineEntity, NetworkError> = headlineRepository.getHeadlines()
}
