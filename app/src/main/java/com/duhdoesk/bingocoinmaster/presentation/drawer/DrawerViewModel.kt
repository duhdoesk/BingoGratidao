package com.duhdoesk.bingocoinmaster.presentation.drawer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duhdoesk.bingocoinmaster.model.Character
import com.duhdoesk.bingocoinmaster.model.Session
import com.duhdoesk.bingocoinmaster.repository.CharacterRepository
import com.duhdoesk.bingocoinmaster.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DrawerState {
    object Loading : DrawerState()
    object Ready : DrawerState()
    data class Drawing(val lastCharacter: Character) : DrawerState()
    data class Finished(val lastCharacter: Character) : DrawerState()
}

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val cRepo: CharacterRepository,
    private val sRepo: SessionRepository
) : ViewModel() {

    //    state variables
    private val _drawState = MutableStateFlow<DrawerState>(DrawerState.Loading)
    val state = _drawState.asStateFlow()

    //    session
    private var session: Session? = null

    //    characters
    private var allCharacters = listOf<Character>()
    private var availableCharacters = mutableListOf<Character>()
    private var drawnCharacters = mutableListOf<Character>()

    init {
        checkSavedState()
    }

    private suspend fun getActiveSession() {
        session = sRepo.getActiveSession()
    }

    private suspend fun getAllCharacters() {
        allCharacters = cRepo.getAllCharacters()
    }

    private suspend fun updateDrawnCharactersIds(charId: String) {
        val drawnCharacters = session?.drawnCharacters.orEmpty().plus("$charId,")
        session = session?.copy(drawnCharacters = drawnCharacters)

        session?.let { session ->
            sRepo.setDrawnElementsIds(session.sessionId, drawnCharacters)
        }
    }

    private fun checkSavedState() {
        viewModelScope.launch {

            getActiveSession()
            getAllCharacters()

        }.invokeOnCompletion {

            if (session == null) {
                _drawState.value = DrawerState.Ready
            } else {
                session?.let { session ->

                    val drawnCharactersIdsList =
                        session.drawnCharacters.split(",").onEach { it.trim() }
                            .filterNot { it.isEmpty() }

                    updateDrawnCharactersList(drawnCharactersIdsList)
                    updateAvailableCharactersList(drawnCharactersIdsList)
                    checkDrawingState()
                }
            }

        }
    }

    private fun checkDrawingState() {
        if (drawnCharacters.isNotEmpty()) {
            when (availableCharacters.isEmpty()) {
                true -> finishDrawing()
                false -> _drawState.value = DrawerState.Drawing(drawnCharacters.last())
            }
        } else {
            _drawState.value = DrawerState.Ready
        }
    }

    private fun updateDrawnCharactersList(drawnCharactersIdsList: List<String>) {

        /*
        populate the list of drawn characters at the current session
        */
        drawnCharactersIdsList.forEach { drawnCharacterId ->
            allCharacters.find { character ->
                character.charId == drawnCharacterId
            }?.let {
                drawnCharacters.add(it)
            }
        }

        Log.d("DRAWN LIST", "$drawnCharacters")
    }

    private fun updateAvailableCharactersList(drawnCharactersIdsList: List<String>) {
        /*
        populate the list of the available characters - the ones that has
        not been drawn
        */
        availableCharacters =
            allCharacters.filter {
                it.charId !in drawnCharactersIdsList
            }.toMutableList()

        Log.d("AVAILABLE LIST", "$availableCharacters")
    }

    private fun finishSession(sessionId: Long) {
        viewModelScope.launch {
            sRepo.finishSession(sessionId)
        }
    }

    fun startNewDrawing() {
        val newSession = Session()
        viewModelScope.launch {
            sRepo.createNewSession(newSession)
        }.invokeOnCompletion {
            checkSavedState()
            drawNextCharacter()
        }
    }

    fun drawNextCharacter() {
        availableCharacters.shuffle()
        val nextCharacter = availableCharacters.first()

        drawnCharacters.add(nextCharacter)
        availableCharacters.removeFirst()

        checkDrawingState()

        viewModelScope.launch {
            updateDrawnCharactersIds(nextCharacter.charId)
        }

        Log.d("DRAW NEXT CHARACTER AV", "${availableCharacters.size}")
        Log.d("DRAW NEXT CHARACTER DR", "${drawnCharacters.size}")
    }

    fun finishDrawing() {
        _drawState.value = DrawerState.Finished(drawnCharacters.last())
        session?.let { finishSession(it.sessionId) }
    }

    fun getDrawnCharacters(): List<Character> {
        return drawnCharacters
    }

    fun getAmounts(): Pair<Int, Int> {
        return Pair(drawnCharacters.size, allCharacters.size)
    }
}