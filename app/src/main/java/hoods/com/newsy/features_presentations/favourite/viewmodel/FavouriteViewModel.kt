package hoods.com.newsy.features_presentations.favourite.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.newsy.features_components.favourite.domain.model.FavouriteArticle
import hoods.com.newsy.features_components.favourite.domain.use_cases.FavouriteUseCases
import hoods.com.newsy.features_components.favourite.domain.use_cases.GetAllFavouriteUseCase
import hoods.com.newsy.utils.K
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteUseCase: FavouriteUseCases,
) : ViewModel() {
    var favouriteState by mutableStateOf(FavouriteState())
        private set

    init {
        load()
    }


    fun updateArticle(article: FavouriteArticle) {
        when(article.category){
            K.SEARCH_CATEGORY ->{
                //update search table
            }
            K.HEADLINE_CATEGORY -> {
                // update Headline Table
            }
            else -> {
                // update discover table
            }
        }
    }

    private fun load() {
        favouriteState = favouriteState.copy(
            favouriteArticles = favouriteUseCase
                .getAllFavouriteUseCase()
                .cachedIn(viewModelScope)
        )
    }

}