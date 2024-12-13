package hoods.com.newsy.features_presentations

import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.features_components.core.domain.repository.SettingRepository
import hoods.com.newsy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSettingRepository : SettingRepository {
    private val settingFlow = MutableStateFlow<Resource<Setting>>(
        Resource.Success(Setting(0, 0))
    )

    override suspend fun getSetting(): Flow<Resource<Setting>> {
        return settingFlow
    }

    override suspend fun insertSetting(setting: Setting) {
        settingFlow.value = Resource.Success(setting)
    }
}