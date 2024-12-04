package hoods.com.newsy.features.components.headline.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.core.data.servicebuilder.ServiceBuilder
import hoods.com.newsy.features.components.headline.data.api.HeadlineApi
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class HeadlineServiceModule {

    @Provides
    @Singleton
    fun provideHeadlineService(serviceBuilder: ServiceBuilder) = serviceBuilder.buildService(HeadlineApi::class.java)

}
