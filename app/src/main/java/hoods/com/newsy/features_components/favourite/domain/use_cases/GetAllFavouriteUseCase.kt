package hoods.com.newsy.features_components.favourite.domain.use_cases

import androidx.paging.PagingData
import hoods.com.newsy.features_components.favourite.domain.model.FavouriteArticle
import hoods.com.newsy.features_components.favourite.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavouriteUseCase(
    private val repository: FavouriteRepository,
) {
    operator fun invoke(): Flow<PagingData<FavouriteArticle>> =
        repository.getAllFavouriteArticle
}