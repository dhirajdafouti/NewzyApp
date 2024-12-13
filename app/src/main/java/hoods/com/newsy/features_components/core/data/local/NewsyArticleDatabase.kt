package hoods.com.newsy.features_components.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hoods.com.newsy.features_components.core.data.local.dao.SettingDao
import hoods.com.newsy.features_components.core.data.local.models.SettingsDto
import hoods.com.newsy.features_components.detail.data.dao.DetailDao
import hoods.com.newsy.features_components.discover.data.local.dao.DiscoverArticleDao
import hoods.com.newsy.features_components.discover.data.local.dao.DiscoverRemoteKeyDao
import hoods.com.newsy.features_components.discover.data.local.models.DiscoverArticleDto
import hoods.com.newsy.features_components.discover.data.local.models.DiscoverKeys
import hoods.com.newsy.features_components.favourite.data.dao.FavouriteDao
import hoods.com.newsy.features_components.headline.data.local.dao.HeadlineDao
import hoods.com.newsy.features_components.headline.data.local.dao.HeadlineRemoteKeyDao
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineDto
import hoods.com.newsy.features_components.headline.data.local.model.HeadlineRemoteKey
import hoods.com.newsy.features_components.search.data.local.dao.SearchArticleDao
import hoods.com.newsy.features_components.search.data.local.dao.SearchRemoteKeyDao
import hoods.com.newsy.features_components.search.data.local.models.SearchDto
import hoods.com.newsy.features_components.search.data.local.models.SearchRemoteKey

@Database(
    entities = [
        HeadlineDao::class,
        HeadlineDto::class,
        HeadlineRemoteKey::class,
        DiscoverArticleDto::class,
        DiscoverKeys::class,
        SearchDto::class,
        SearchRemoteKey::class,
        SettingsDto::class
    ],
    exportSchema = false,
    version = 1
)
abstract class NewsyArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineRemoteDao(): HeadlineRemoteKeyDao
    abstract fun discoverArticleDao(): DiscoverArticleDao
    abstract fun discoverRemoteKeyDao(): DiscoverRemoteKeyDao
    abstract fun detailDao(): DetailDao
    abstract fun searchArticleDao(): SearchArticleDao
    abstract fun searchKeyDao(): SearchRemoteKeyDao
    abstract fun favouriteDao(): FavouriteDao
    abstract fun settingDao(): SettingDao
}