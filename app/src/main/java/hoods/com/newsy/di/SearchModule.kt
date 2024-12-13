package hoods.com.newsy.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.search.data.local.models.SearchDto
import hoods.com.newsy.features_components.search.data.mappers.SearchMapper
import hoods.com.newsy.features_components.search.data.remote.SearchApi
import hoods.com.newsy.features_components.search.data.repository.SearchRepositoryImpl
import hoods.com.newsy.features_components.search.domain.models.SearchArticle
import hoods.com.newsy.features_components.search.domain.repository.SearchRepository
import hoods.com.newsy.features_components.search.domain.use_cases.FetchSearchArticleUseCase
import hoods.com.newsy.features_components.search.domain.use_cases.SearchUseCases
import hoods.com.newsy.features_components.search.domain.use_cases.UpdateFavouriteUseCase
import hoods.com.newsy.utils.K
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideSearchMapper(): Mapper<SearchDto, SearchArticle> =
        SearchMapper()

    @Provides
    @Singleton
    fun provideSearchApi(): SearchApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(SearchApi::class.java)
    }


    @Provides
    @Singleton
    fun provideSearchRepository(
        database: NewsyArticleDatabase,
        searchMapper: Mapper<SearchDto, SearchArticle>,
        searchApi: SearchApi,
    ): SearchRepository =
        SearchRepositoryImpl(
            api = searchApi,
            database = database,
            mapper = searchMapper
        )

    @Provides
    @Singleton
    fun provideSearchUseCases(
        repository: SearchRepository,
    ): SearchUseCases = SearchUseCases(
        fetchSearchArticleUseCase = FetchSearchArticleUseCase(repository),
        updateFavouriteUseCase = UpdateFavouriteUseCase(repository)
    )
}







