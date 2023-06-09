package com.duhdoesk.bingocoinmaster.repository

import com.duhdoesk.bingocoinmaster.data.CardDao
import com.duhdoesk.bingocoinmaster.model.Card
import javax.inject.Inject

class CardRepository @Inject constructor(private val cardDao: CardDao) {
    suspend fun getAllCards() : List<Card> = cardDao.getAllCards()
    suspend fun getCardById(id: String) : Card = cardDao.getCardById(id)
}