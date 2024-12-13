package hoods.com.newsy.features_components.discover.domain.use_cases

import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.discover.domain.repository.DiscoverRepository

class UpdateFavouriteDiscoverArticleUseCase(
    private val repository: DiscoverRepository,
) {
    suspend operator fun invoke(article: NewsyArticle) {
        repository.updateFavouriteDiscoverCategory(article)
    }
}