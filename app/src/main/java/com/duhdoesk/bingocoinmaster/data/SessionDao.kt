package com.duhdoesk.bingocoinmaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duhdoesk.bingocoinmaster.model.Session

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewSession(session: Session) : Long

    @Query("UPDATE session_table SET is_completed = 1 WHERE session_id = :sessionId")
    suspend fun finishSession(sessionId: Long)

    @Query("SELECT * FROM session_table WHERE is_completed = 0 LIMIT 1")
    suspend fun getActiveSession(): Session?

    @Query("SELECT drawn_characters FROM session_table WHERE session_id = :sessionId")
    suspend fun getDrawnElementsIds(sessionId: Long): String

    @Query("UPDATE session_table SET drawn_characters = :idList WHERE session_id = :sessionId")
    suspend fun setDrawnElementsIds(sessionId: Long, idList: String)

}