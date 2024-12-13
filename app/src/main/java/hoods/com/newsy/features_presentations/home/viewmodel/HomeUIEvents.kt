package hoods.com.newsy.features_presentations.home.viewmodel

import hoods.com.newsy.features_components.core.domain.models.NewsyArticle
import hoods.com.newsy.utils.ArticleCategory

sealed class HomeUIEvents{
    object ViewMoreClicked:HomeUIEvents()
    data class ArticleClicked(val url:String):HomeUIEvents()
    data class CategoryChange(val category: ArticleCategory) : HomeUIEvents()
    data class PreferencePanelToggle(val isOpen: Boolean) : HomeUIEvents()
    data class OnHeadLineFavouriteChange(val article: NewsyArticle) : HomeUIEvents()
    data class OnDiscoverFavouriteChange(val article: NewsyArticle) : HomeUIEvents()
}