package com.duhdoesk.bingocoinmaster.di

import android.content.Context
import androidx.room.Room
import com.duhdoesk.bingocoinmaster.data.AppDatabase
import com.duhdoesk.bingocoinmaster.data.CardDao
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
    fun provideCardDao(appDatabase: AppDatabase) : CardDao =
        appDatabase.cardDao()

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