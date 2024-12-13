package hoods.com.newsy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.favourite.data.dao.FavouriteDao
import hoods.com.newsy.features_components.favourite.data.mapper.FavouriteMapper
import hoods.com.newsy.features_components.favourite.data.models.FavouriteDto
import hoods.com.newsy.features_components.favourite.data.repository.FavouriteRepoImpl
import hoods.com.newsy.features_components.favourite.domain.model.FavouriteArticle
import hoods.com.newsy.features_components.favourite.domain.repository.FavouriteRepository
import hoods.com.newsy.features_components.favourite.domain.use_cases.FavouriteUseCases
import hoods.com.newsy.features_components.favourite.domain.use_cases.GetAllFavouriteUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavouriteModule {

    @Provides
    @Singleton
    fun provideFavouriteDao(database: NewsyArticleDatabase) =
        database.favouriteDao()

    @Provides
    @Singleton
    fun provideFavouriteMapper(): Mapper<FavouriteDto, FavouriteArticle> =
        FavouriteMapper()

    @Provides
    @Singleton
    fun provideRepository(
        dao: FavouriteDao,
        favouriteMapper: Mapper<FavouriteDto, FavouriteArticle>,
    ): FavouriteRepository = FavouriteRepoImpl(
        dao, favouriteMapper
    )

    @Provides
    @Singleton
    fun provideFavouriteUseCases(
        repository: FavouriteRepository,
    ): FavouriteUseCases =
        FavouriteUseCases(
            getAllFavouriteUseCase = GetAllFavouriteUseCase(repository)
        )

}