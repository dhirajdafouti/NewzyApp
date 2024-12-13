package hoods.com.newsy.features_presentations.search.viewmodel

import hoods.com.newsy.features_components.search.domain.models.SearchArticle

sealed class SearchEvents{
    data class OnSearchChange(val query:String): SearchEvents()
    object SubmitSearch: SearchEvents()
    data class SaveSearchHistory(val historyQuery:String): SearchEvents()
    object ClearSearchHistory: SearchEvents()
    data class OnFavouriteChange(val searchArticle:SearchArticle): SearchEvents()
}
