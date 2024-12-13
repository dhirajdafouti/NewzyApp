package hoods.com.newsy.features_components.headline.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineRemoteKey

@Dao
interface HeadlineRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<HeadlineRemoteKey>)

    @Query("SELECT * FROM headline_key WHERE article_id =:id")
    suspend fun getRemoteKeyByArticleId(id: String): HeadlineRemoteKey?

    @Query("DELETE FROM headline_key")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM headline_key ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}








