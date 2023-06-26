package com.duhdoesk.bingocoinmaster.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "char_table")
data class Character(
    @PrimaryKey
    val charId: String,

    @ColumnInfo(name = "char_name")
    val name: String,

    @ColumnInfo(name = "char_picture")
    val picture: String
)
