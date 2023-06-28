package com.duhdoesk.bingocoinmaster.repository

import com.duhdoesk.bingocoinmaster.data.SessionDao
import com.duhdoesk.bingocoinmaster.model.Session
import javax.inject.Inject

class SessionRepository @Inject constructor(private val sessionDao: SessionDao) {

    suspend fun createNewSession(session: Session) =
        sessionDao.createNewSession(session)

    suspend fun finishSession(sessionId: Long) =
        sessionDao.finishSession(sessionId)

    suspend fun getActiveSession(): Session? =
        sessionDao.getActiveSession()

    suspend fun getDrawnElementsIds(sessionId: Long): String =
        sessionDao.getDrawnElementsIds(sessionId)

    suspend fun setDrawnElementsIds(sessionId: Long, idList: String) =
        sessionDao.setDrawnElementsIds(sessionId, idList)

}