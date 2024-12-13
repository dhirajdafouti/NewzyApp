package hoods.com.newsy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.detail.data.dao.DetailDao
import hoods.com.newsy.features_components.detail.data.repository.DetailRepositoryImpl
import hoods.com.newsy.features_components.detail.domain.repository.DetailRepository
import hoods.com.newsy.features_components.detail.domain.use_cases.DetailUseCases
import hoods.com.newsy.features_components.detail.domain.use_cases.GetDetailDiscoverArticleUseCase
import hoods.com.newsy.features_components.detail.domain.use_cases.GetDetailHeadlineArticleUseCase
import hoods.com.newsy.features_components.detail.domain.use_cases.GetDetailSearchArticleUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {
    @Provides
    @Singleton
    fun provideDetailRepository(
        detailDao: DetailDao,
    ): DetailRepository =
        DetailRepositoryImpl(detailDao)

    @Provides
    @Singleton
    fun provideDetailDao(
        newsyArticleDatabase: NewsyArticleDatabase,
    ): DetailDao = newsyArticleDatabase.detailDao()

    @Provides
    @Singleton
    fun provideDetailUsecases(repo: DetailRepository): DetailUseCases = DetailUseCases(
        getDetailDiscoverArticleUseCase = GetDetailDiscoverArticleUseCase(
            repo
        ),
        getDetailHeadlineArticleUseCase = GetDetailHeadlineArticleUseCase(
            repo
        ),
        getDetailSearchArticleUseCase = GetDetailSearchArticleUseCase(
            repo
        )

    )

}