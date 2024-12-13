package hoods.com.newsy.features_presentations.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.newsy.features_components.search.domain.use_cases.SearchUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCases: SearchUseCases,
) : ViewModel() {
    var searchState by mutableStateOf(SearchState())
        private set

    private fun search(query: String) {
        searchState = searchState.copy(
            searchArticles = searchUseCases.fetchSearchArticleUseCase(query)
        )
    }

    fun onSearchEvents(searchEvents: SearchEvents) {
        when (searchEvents) {
            is SearchEvents.OnSearchChange -> {
                searchState = searchState.copy(
                    query = searchEvents.query
                )
            }

            is SearchEvents.SaveSearchHistory -> {
                searchState = searchState.copy(
                    searchHistory = searchState.searchHistory + listOf(searchEvents.historyQuery)
                )
            }

            SearchEvents.SubmitSearch -> {
                search(searchState.query)
            }

            SearchEvents.ClearSearchHistory -> {
                searchState = searchState.copy(
                    searchHistory = emptyList()
                )
            }

            is SearchEvents.OnFavouriteChange -> {
                searchEvents.searchArticle.copy(
                    favourite = !searchEvents.searchArticle.favourite
                ).also {
                    viewModelScope.launch {
                        searchUseCases.updateFavouriteUseCase(it)
                    }
                }
            }

        }
    }

}







