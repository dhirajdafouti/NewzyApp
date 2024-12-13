package hoods.com.newsy.features_components.core.domain.use_cases

import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.features_components.core.domain.repository.SettingRepository
import hoods.com.newsy.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetSettingUseCase(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke(): Flow<Resource<Setting>> {
        return settingRepository.getSetting()
    }
}