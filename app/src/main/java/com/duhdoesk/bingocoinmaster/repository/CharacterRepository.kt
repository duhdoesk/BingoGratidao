package com.duhdoesk.bingocoinmaster.repository

import com.duhdoesk.bingocoinmaster.data.CharacterDao
import com.duhdoesk.bingocoinmaster.model.Character
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val characterDao: CharacterDao) {
    suspend fun getAllCharacters() : List<Character> = characterDao.getAllCharacters()
    suspend fun getCharacterById(id: String) : Character? = characterDao.getCharacterById(id)
}