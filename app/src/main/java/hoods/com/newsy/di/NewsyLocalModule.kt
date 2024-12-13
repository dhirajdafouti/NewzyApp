package hoods.com.newsy.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hoods.com.newsy.features_components.core.data.local.NewsyArticleDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsyLocalModule {

    @Singleton
    @Provides
    fun provideNewsyDatabase(
        @ApplicationContext context: Context,
    ): NewsyArticleDatabase {
        return Room.databaseBuilder(
            context,
            NewsyArticleDatabase::class.java,
            "newsy_db"
        )
            .build()
    }

}