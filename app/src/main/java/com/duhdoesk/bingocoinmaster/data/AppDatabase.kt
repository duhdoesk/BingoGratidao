package com.duhdoesk.bingocoinmaster.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duhdoesk.bingocoinmaster.model.Character
import com.duhdoesk.bingocoinmaster.model.Session

@Database(
    entities = [Character::class, Session::class],
    exportSchema = true,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao

    abstract fun sessionDao() : SessionDao
}