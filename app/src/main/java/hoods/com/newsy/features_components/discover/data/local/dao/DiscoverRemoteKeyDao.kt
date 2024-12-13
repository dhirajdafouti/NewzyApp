package hoods.com.newsy.features_components.discover.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hoods.com.newsy.features_components.discover.data.local.models.DiscoverKeys

@Dao
interface DiscoverRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey:List<DiscoverKeys>)

    @Query(
        "SELECT * FROM discover_keys WHERE article_id=:id"
    )
    suspend fun getRemoteKeyByArticleId(id:String):DiscoverKeys?

    @Query("DELETE FROM discover_keys WHERE current_category=:category")
    suspend fun clearRemoteKey(category:String)

    @Query(
        "SELECT created_at FROM discover_keys ORDER BY created_at DESC LIMIT 1"
    )
    suspend fun getCreationTime():Long?
    @Query(
        "SELECT current_category FROM discover_keys"
    )
    suspend fun getCurrentCategory():String

    @Query(
        "SELECT DISTINCT current_category FROM discover_keys"
    )
    suspend fun getAllAvailableCategories():List<String>

    @Query("UPDATE discover_keys SET current_category =:category")
    suspend fun updateCategory(category: String)
}