package hoods.com.newsy.features_components.core.domain.repository

import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun getSetting(): Flow<Resource<Setting>>
    suspend fun insertSetting(setting: Setting)
}