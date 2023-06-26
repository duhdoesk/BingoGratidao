package com.duhdoesk.bingocoinmaster.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
data class Card(
    @PrimaryKey
    val cardId: String,

    @ColumnInfo(name = "card_name")
    val name: String,

    @ColumnInfo(name = "card_picture")
    val picture: String
)
