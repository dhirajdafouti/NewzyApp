package hoods.com.newsy.features_components.search.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hoods.com.newsy.features_components.search.data.local.models.SearchRemoteKey

@Dao
interface SearchRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey:List<SearchRemoteKey>)

    @Query("SELECT * FROM search_key_table WHERE article_id=:id")
    suspend fun getRemoteKeyArticleById(id:String):SearchRemoteKey?

    @Query(
        "DELETE FROM search_key_table"
    )
    suspend fun clearRemoteKeys()

    @Query(
        "SELECT created_at FROM search_key_table ORDER BY created_at DESC LIMIT 1"
    )
    suspend fun getCreationTime():Long?

}