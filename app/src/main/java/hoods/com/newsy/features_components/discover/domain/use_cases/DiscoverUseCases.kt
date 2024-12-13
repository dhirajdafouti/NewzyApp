package hoods.com.newsy.features_components.discover.domain.use_cases

data class DiscoverUseCases(
    val fetchDiscoverArticleUseCase: FetchDiscoverArticleUseCase,
    val updateCurrentCategoryUseCase: UpdateCurrentCategoryUseCase,
    val getDiscoverCurrentCategoryUseCase: GetDiscoverCurrentCategoryUseCase,
    val updateFavouriteDiscoverArticleUseCase: UpdateFavouriteDiscoverArticleUseCase,
)