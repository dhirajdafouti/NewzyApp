package hoods.com.newsy.features_components.core.domain.use_cases

import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.features_components.core.domain.repository.SettingRepository

class UpdateSettingUseCase(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke(setting: Setting) {
        settingRepository.insertSetting(setting)
    }
}