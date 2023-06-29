package com.duhdoesk.bingocoinmaster.presentation.drawer

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

sealed class DrawerState() {
    object Loading : DrawerState()
    object Ready : DrawerState()
    data class Drawing(val drawnCharacters: List<Character>) : DrawerState()
    data class Finished(val drawnCharacters: List<Character>) : DrawerState()
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

    private fun checkSavedState() {
        viewModelScope.launch {
            session = sRepo.getActiveSession()
            allCharacters = cRepo.getAllCharacters()

            if (session == null) {
                _drawState.value = DrawerState.Ready
            } else {
                session?.let { session ->

                    val drawnCharactersIdsList =
                        session.drawnCharacters.split(",").onEach { it.trim() }
                            .filterNot { it.isEmpty() }

                    populateCharactersLists(drawnCharactersIdsList)
                    checkDrawingState()
                }
            }
        }
    }

    private fun populateCharactersLists(drawnCharactersIdsList: List<String>) {

        /*
        populate the list of drawn characters at the current session
        */
        drawnCharactersIdsList.forEach { drawnCharacterId ->
            allCharacters.find { character ->
                character.charId == drawnCharacterId
            }?.let {
                drawnCharacters.add(it)
            }

        /*
        populate the list of the available characters - the ones that has
        not been drawn
        */
        availableCharacters =
            allCharacters.filter {
                it.charId !in drawnCharactersIdsList
            }.toMutableList()

        }
    }

    private fun checkDrawingState() {
        when (availableCharacters.isEmpty()) {
            true -> _drawState.value = DrawerState.Finished(drawnCharacters)
            false -> _drawState.value = DrawerState.Drawing(drawnCharacters)
        }
    }
}