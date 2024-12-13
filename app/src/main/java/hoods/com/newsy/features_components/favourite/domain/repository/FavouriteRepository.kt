package hoods.com.newsy.features_components.favourite.domain.repository

import androidx.paging.PagingData
import hoods.com.newsy.features_components.favourite.domain.model.FavouriteArticle
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    val getAllFavouriteArticle: Flow<PagingData<FavouriteArticle>>
}