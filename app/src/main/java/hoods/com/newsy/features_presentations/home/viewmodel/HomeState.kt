package hoods.com.newsy.features_presentations.home.viewmodel

import androidx.paging.PagingData
import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.features_components.core.domain.models.Setting
import hoods.com.newsy.utils.ArticleCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val headlineArticles: Flow<PagingData<NewsyArticle>> = emptyFlow(),
    val discoverArticles: Flow<PagingData<NewsyArticle>> = emptyFlow(),
    val selectedDiscoverCategory: ArticleCategory = ArticleCategory.SPORTS,
    val selectedHeadlineCategory: ArticleCategory = ArticleCategory.BUSINESS,
    val setting: Setting = Setting(0, 0),
)
