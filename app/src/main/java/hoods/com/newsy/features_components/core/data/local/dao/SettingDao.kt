package hoods.com.newsy.features_components.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hoods.com.newsy.features_components.core.data.local.models.SettingsDto

@Dao
interface SettingDao {
    @Query("SELECT * FROM setting_table")
    suspend fun getSettings(): SettingsDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(settingsDto: SettingsDto)
}