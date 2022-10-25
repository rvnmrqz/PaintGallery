package com.arvinmarquez.paintgallery.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arvinmarquez.paintgallery.data.data_source.local.PaintingDao
import com.arvinmarquez.paintgallery.core.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDb {
        return Room.databaseBuilder(
            context,
            AppDb::class.java,
            AppDb.DB_NAME
        )
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Provides
    fun providesPaintingDao(database: AppDb) : PaintingDao = database.paintingDao()
}