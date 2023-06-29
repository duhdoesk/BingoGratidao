package com.duhdoesk.bingocoinmaster.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "session_id")
    val sessionId: Long,

    @ColumnInfo(name = "drawn_characters")
    val drawnCharacters: String,

    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean
)