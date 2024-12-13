package hoods.com.newsy.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.data.remote.models.Article
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.headline.data.local.dao.HeadlineDao
import hoods.com.newsy.features_components.headline.data.local.dao.HeadlineRemoteKeyDao
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto
import hoods.com.newsy.features_components.headline.data.mapper.ArticleHeadlineDtoMapper
import hoods.com.newsy.features_components.headline.data.mapper.HeadlineMapper
import hoods.com.newsy.features_components.headline.data.paging.HeadlineRemoteMediator
import hoods.com.newsy.features_components.headline.data.remote.HeadlineApi
import hoods.com.newsy.features_components.headline.data.repository.HeadlineRepositoryImpl
import hoods.com.newsy.features_components.headline.domain.repository.HeadlineRepository
import hoods.com.newsy.features_components.headline.domain.use_cases.FetchHeadlineArticleUseCase
import hoods.com.newsy.features_components.headline.domain.use_cases.HeadlineUseCases
import hoods.com.newsy.features_components.headline.domain.use_cases.UpdateHeadlineFavouriteUseCase
import hoods.com.newsy.utils.K
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeadlineModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideHeadlineApi(): HeadlineApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(HeadlineApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeadlineMediator(
        api: HeadlineApi,
        database: NewsyArticleDatabase
    ): HeadlineRemoteMediator {
        return HeadlineRemoteMediator(
            api, database
        )
    }


    @Provides
    @Singleton
    fun provideHeadlineRepository(
        headlineDao: HeadlineDao,
        headlineRemoteMediator: HeadlineRemoteMediator,
        mapper: Mapper<HeadlineDto, NewsyArticle>,
    ): HeadlineRepository {
        return HeadlineRepositoryImpl(
            mediator = headlineRemoteMediator,
            headlineDao = headlineDao,
            mapper = mapper
        )
    }

    @Provides
    @Singleton
    fun provideHeadlineDao(
        database: NewsyArticleDatabase,
    ): HeadlineDao = database.headlineDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(
        database: NewsyArticleDatabase,
    ): HeadlineRemoteKeyDao = database.headlineRemoteDao()

    @Provides
    @Singleton
    fun provideHeadlineUseCases(
        repository: HeadlineRepository,
    ): HeadlineUseCases =
        HeadlineUseCases(
            fetchHeadlineArticleUseCase = FetchHeadlineArticleUseCase(
                repository = repository
            ),
            updateHeadlineFavouriteUseCase = UpdateHeadlineFavouriteUseCase(
                repository = repository
            )
        )

    @Provides
    @Singleton
    fun provideArticleToHeadlineMapper(): Mapper<Article, HeadlineDto> = ArticleHeadlineDtoMapper()

    @Provides
    @Singleton
    fun provideHeadlineMapper(): Mapper<HeadlineDto, NewsyArticle> = HeadlineMapper()

}