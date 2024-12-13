package hoods.com.newsy.features_presentations.headline.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.core.domain.use_cases.SettingUseCases
import hoods.com.newsy.features_components.headline.domain.use_cases.HeadlineUseCases
import hoods.com.newsy.utils.Resource
import hoods.com.newsy.utils.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlineViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases,
    private val settingUseCases: SettingUseCases,
) : ViewModel() {
    var headlineState by mutableStateOf(HeadlineState())
        private set


    init {
        initSettings()
    }

    init {
        initHeadlineArticleData()
    }

    private fun initSettings() {
        viewModelScope.launch {
            val settings = settingUseCases.getSettingUseCase()
            settings.collectLatest {
                if (it is Resource.Success) {
                    headlineState = headlineState.copy(
                        setting = it.data
                    )
                }
            }
        }
    }

    private fun initHeadlineArticleData() {
        val currentCountryCode =
            Utils.countryCodeList[headlineState.setting.preferredCountryIndex].code
        val currentLanguageCode =
            Utils.languageCodeList[headlineState.setting.preferredCountryIndex].code

        headlineState = headlineState.copy(
            headlineArticles = headlineUseCases
                .fetchHeadlineArticleUseCase(
                    headlineState.selectedHeadlineCategory.category,
                    countryCode = currentCountryCode,
                    languageCode = currentLanguageCode
                ).cachedIn(viewModelScope)
        )

    }

    fun onFavouriteChange(article: NewsyArticle) {
        viewModelScope.launch {
            val isFavourite = article.favourite
            article.copy(
                favourite = !isFavourite
            ).also {
                headlineUseCases.updateHeadlineFavouriteUseCase(
                    it
                )
            }
        }
    }


}