package hoods.com.newsy.features_presentations.setting.viewmodel

data class SettingState(
    val countryIndex: Int = 0,
    val languageIndex: Int = 0,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
)