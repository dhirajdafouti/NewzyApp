package hoods.com.newsy.features_components.detail.domain.use_cases

data class DetailUseCases(
    val getDetailHeadlineArticleUseCase: GetDetailHeadlineArticleUseCase,
    val getDetailDiscoverArticleUseCase: GetDetailDiscoverArticleUseCase,
    val getDetailSearchArticleUseCase: GetDetailSearchArticleUseCase
)