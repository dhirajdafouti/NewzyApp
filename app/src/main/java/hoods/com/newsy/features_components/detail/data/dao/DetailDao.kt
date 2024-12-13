package hoods.com.newsy.features_components.detail.data.dao

import androidx.room.Dao
import androidx.room.Query
import hoods.com.newsy.features_components.detail.data.model.DetailDto

@Dao
interface DetailDao {

    @Query("SELECT * FROM headline_table WHERE id=:id")
    suspend fun getHeadlineArticleById(id: Int): DetailDto


    @Query("SELECT * FROM discover_article WHERE id=:id")
    suspend fun getDiscoverArticleById(id: Int): DetailDto

    @Query("SELECT * FROM search_table WHERE id=:id")
    suspend fun getSearchArticleById(id: Int): DetailDto
}