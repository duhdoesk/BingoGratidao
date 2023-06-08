package com.duhdoesk.bingocoinmaster.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "card_table")
data class Card(
    @PrimaryKey
    val cardId: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "card_name")
    val name: String,

    @ColumnInfo(name = "card_picture")
    val picture: String
)
