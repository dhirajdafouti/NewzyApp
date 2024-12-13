package hoods.com.newsy.features_components.favourite.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import hoods.com.newsy.features_components.favourite.data.models.FavouriteDto

@Dao
interface FavouriteDao {
    @Query(
        """
            SELECT * FROM discover_article WHERE favourite=1
            UNION
            SELECT * FROM headline_table WHERE favourite=1
            UNION
            SELECT * FROM search_table WHERE favourite=1
        """
    )
    fun getAllFavouriteArticles(): PagingSource<Int, FavouriteDto>

    @Query(
        "UPDATE discover_article SET favourite=:isFavourite WHERE id=:id"
    )
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int
    @Query(
        "UPDATE headline_table SET favourite=:isFavourite WHERE id=:id"
    )
    suspend fun updateHeadlineArticle(isFavourite: Boolean, id: Int): Int
    @Query(
        "UPDATE search_table SET favourite=:isFavourite WHERE id=:id"
    )
    suspend fun updateSearchArticle(isFavourite: Boolean, id: Int): Int
}