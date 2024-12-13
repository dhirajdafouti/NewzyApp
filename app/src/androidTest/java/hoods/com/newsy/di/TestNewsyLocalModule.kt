package hoods.com.newsy.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NewsyLocalModule::class]
)
object TestNewsyLocalModule {

    @Singleton
    @Provides
    fun provideNewsyDatabase(
        @ApplicationContext context: Context,
    ): NewsyArticleDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            NewsyArticleDatabase::class.java,
        ).allowMainThreadQueries()
            .build()
    }

}