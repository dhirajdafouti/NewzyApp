package hoods.com.newsy.features.components.headline.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features.components.headline.data.repository.HeadlineRepositoryImpl
import hoods.com.newsy.features.components.headline.domain.repository.HeadlineRepository
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class HeadlineRepositoryModule {

    @Binds
    @Singleton
  abstract fun provideHeadlineRepository(repository: HeadlineRepositoryImpl): HeadlineRepository
}
