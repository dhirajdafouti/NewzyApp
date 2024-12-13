package hoods.com.newsy.features_components.detail.domain.use_cases

import hoods.com.newsy.features_components.detail.domain.models.DetailArticle
import hoods.com.newsy.features_components.detail.domain.repository.DetailRepository
import hoods.com.newsy.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetDetailSearchArticleUseCase(
    private val detailRepository: DetailRepository,
) {
    suspend operator fun invoke(id: Int): Flow<Resource<DetailArticle>> =
        detailRepository.getSearchArticleById(id)
}