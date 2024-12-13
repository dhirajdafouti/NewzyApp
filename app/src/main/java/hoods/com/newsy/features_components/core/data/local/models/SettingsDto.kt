package hoods.com.newsy.features_components.core.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("setting_table")
data class SettingsDto(
    @PrimaryKey
    val id: Int = 0,
    val preferredLanguage: Int,
    val preferredCountry: Int,
)
