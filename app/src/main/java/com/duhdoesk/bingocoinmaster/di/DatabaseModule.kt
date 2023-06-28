package com.duhdoesk.bingocoinmaster.di

import android.content.Context
import androidx.room.Room
import com.duhdoesk.bingocoinmaster.data.AppDatabase
import com.duhdoesk.bingocoinmaster.data.CharacterDao
import com.duhdoesk.bingocoinmaster.data.SessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideSessionDao(appDatabase: AppDatabase) : SessionDao =
        appDatabase.sessionDao()

    @Singleton
    @Provides
    fun provideCharacterDao(appDatabase: AppDatabase) : CharacterDao =
        appDatabase.characterDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "appDatabase"
        )
            .createFromAsset("coin.db")
            .build()

}