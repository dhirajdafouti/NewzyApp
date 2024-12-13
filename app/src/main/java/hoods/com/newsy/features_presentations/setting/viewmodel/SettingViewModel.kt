package hoods.com.newsy.features_presentations.setting.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.features_components.core.domain.use_cases.SettingUseCases
import hoods.com.newsy.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCases,
) : ViewModel() {
    var settingState by mutableStateOf(SettingState())
        private set

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val settingData = settingUseCase.getSettingUseCase()
            settingData.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        settingState = settingState.copy(
                            isError = false,
                            errorMessage = null,
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        settingState = settingState.copy(
                            isError = false,
                            errorMessage = null,
                            isLoading = false,
                            countryIndex = it.data.preferredCountryIndex,
                            languageIndex = it.data.preferredLanguageIndex,
                        )
                    }

                    is Resource.Error -> {
                        settingState = settingState.copy(
                            isError = true,
                            errorMessage = it.error?.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onSettingEvents(settingsEvents: SettingsEvents) {
        when (settingsEvents) {
            is SettingsEvents.CountryChange -> {
                settingState = settingState.copy(
                    countryIndex = settingsEvents.countryIndex
                )
            }

            is SettingsEvents.LanguageChange -> {
                settingState = settingState.copy(
                    languageIndex = settingsEvents.languageIndex
                )
            }

            SettingsEvents.SaveSetting -> {
                viewModelScope.launch {
                    val newSettings = Setting(
                        preferredCountryIndex = settingState.countryIndex,
                        preferredLanguageIndex = settingState.languageIndex,
                    )
                    settingUseCase.insertSettingUseCase(
                        newSettings
                    )
                }
            }
        }
    }


}