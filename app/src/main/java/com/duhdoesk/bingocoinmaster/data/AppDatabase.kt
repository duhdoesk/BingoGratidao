package com.duhdoesk.bingocoinmaster.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duhdoesk.bingocoinmaster.model.Card

@Database(
    entities = [Card::class],
    exportSchema = true,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao() : CardDao
}