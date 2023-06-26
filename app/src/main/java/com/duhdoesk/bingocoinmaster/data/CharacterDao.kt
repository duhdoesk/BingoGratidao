package com.duhdoesk.bingocoinmaster.data

import androidx.room.Dao
import androidx.room.Query
import com.duhdoesk.bingocoinmaster.model.Character

@Dao
interface CharacterDao {

    @Query("SELECT * from char_table")
    suspend fun getAllCharacters() : List<Character>

    @Query("SELECT * from char_table where charId =:id")
    suspend fun getCharacterById(id: String) : Character?
}