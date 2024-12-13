package hoods.com.newsy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import hoods.com.newsy.features_components.core.data.local.dao.SettingDao
import hoods.com.newsy.features_components.core.data.local.models.SettingsDto
import hoods.com.newsy.features_components.core.data.mapper.SettingMapper
import hoods.com.newsy.features_components.core.data.repository.SettingRepositoryImpl
import hoods.com.newsy.features_components.core.domain.mapper.Mapper
import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.features_components.core.domain.repository.SettingRepository
import hoods.com.newsy.features_components.core.domain.use_cases.GetSettingUseCase
import hoods.com.newsy.features_components.core.domain.use_cases.InsertSettingUseCase
import hoods.com.newsy.features_components.core.domain.use_cases.SettingUseCases
import hoods.com.newsy.features_components.core.domain.use_cases.UpdateSettingUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {
    @Provides
    @Singleton
    fun provideSettingDao(database: NewsyArticleDatabase): SettingDao =
        database.settingDao()

    @Provides
    @Singleton
    fun provideRepository(
        settingDao: SettingDao,
        mapper: Mapper<SettingsDto, Setting>,
    ): SettingRepository =
        SettingRepositoryImpl(settingDao, mapper)

    @Provides
    @Singleton
    fun provideMapper(): Mapper<SettingsDto?, Setting> = SettingMapper()

    @Provides
    @Singleton
    fun provideSettingUseCases(
        repository: SettingRepository,
    ): SettingUseCases = SettingUseCases(
        getSettingUseCase = GetSettingUseCase(repository),
        insertSettingUseCase = InsertSettingUseCase(repository),
        updateSettingUseCase = UpdateSettingUseCase(repository)
    )

}