package hoods.com.newsy.features_components.headline.domain.use_cases

import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headline.domain.repository.HeadlineRepository

class UpdateHeadlineFavouriteUseCase(
    private val repository: HeadlineRepository,
) {
    suspend operator fun invoke(article: NewsyArticle) {
        repository.updateFavouriteArticle(article)
    }
}