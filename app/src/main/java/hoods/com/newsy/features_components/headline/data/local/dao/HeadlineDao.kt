package hoods.com.newsy.features_components.headline.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {
    @Query("SELECT * FROM headline_table")
    fun getAllHeadlineArticles(): PagingSource<Int, HeadlineDto>

    @Query("SELECT * FROM headline_table WHERE id=:id")
    fun getHeadlineArticle(id: Int): Flow<HeadlineDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeadlineArticle(articles: List<HeadlineDto>)

    @Query(
        "DELETE FROM headline_table WHERE favourite=0"
    )
    suspend fun removeAllHeadlineArticles()

    @Query(
        "DELETE FROM headline_table WHERE id=:id AND favourite=1"
    )
    suspend fun removeFavouriteArticle(id: Int)

    @Query(
        "UPDATE headline_table SET favourite =:isFavourite WHERE id=:id"
    )
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int)

}

fun a(){}







