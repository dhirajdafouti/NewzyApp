package hoods.com.newsy.features_components.search.domain.use_cases

data class SearchUseCases(
    val fetchSearchArticleUseCase: FetchSearchArticleUseCase,
    val updateFavouriteUseCase: UpdateFavouriteUseCase,
)
