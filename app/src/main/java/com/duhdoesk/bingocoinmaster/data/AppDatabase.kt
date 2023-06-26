package com.duhdoesk.bingocoinmaster.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duhdoesk.bingocoinmaster.model.Character

@Database(
    entities = [Character::class],
    exportSchema = true,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao() : CharacterDao
}