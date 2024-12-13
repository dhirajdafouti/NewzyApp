package hoods.com.newsy.features_components.headline.domain.use_cases

import androidx.paging.PagingData
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headline.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow

class FetchHeadlineArticleUseCase(
    private val repository: HeadlineRepository,
) {
    operator fun invoke(
        category: String,
        countryCode: String,
        languageCode: String,
    ): Flow<PagingData<NewsyArticle>> {

        if (category.isBlank()) throw IllegalArgumentException("category can not be empty")
        if (countryCode.isBlank()) throw IllegalArgumentException("country can not be empty")
        if (languageCode.isBlank()) throw IllegalArgumentException("language code can not be empty")

        return repository.fetchHeadlineArticle(
            category, countryCode, languageCode
        )
    }
}