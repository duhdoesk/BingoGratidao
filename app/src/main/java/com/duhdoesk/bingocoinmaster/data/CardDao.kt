package com.duhdoesk.bingocoinmaster.data

import androidx.room.Dao
import androidx.room.Query
import com.duhdoesk.bingocoinmaster.model.Card

@Dao
interface CardDao {

    @Query("SELECT * from card_table")
    suspend fun getAllCards() : List<Card>

    @Query("SELECT * from card_table where cardId =:id")
    suspend fun getCardById(id: String) : Card
}