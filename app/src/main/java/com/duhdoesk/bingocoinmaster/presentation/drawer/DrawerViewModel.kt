package com.duhdoesk.bingocoinmaster.presentation.drawer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duhdoesk.bingocoinmaster.model.Character
import com.duhdoesk.bingocoinmaster.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DrawState {
    object Loading : DrawState()
    object Ready : DrawState()
    data class Drawn(val characterList: List<Character>, val counter: Int) : DrawState()
}

@HiltViewModel
class DrawerViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

//    state variables
    private val _drawState = MutableStateFlow<DrawState>(DrawState.Loading)
    val state = _drawState.asStateFlow()

//    card list
    private val characterList: MutableState<List<Character>> = mutableStateOf(emptyList())

//    card counter
    private val counter = mutableStateOf(0)

    init {
        viewModelScope.launch {
            characterList.value = repository.getAllCharacters()
            _drawState.value = DrawState.Ready
        }
    }
    fun sortNewBingoCard() {
        counter.value += 1

        _drawState.value = DrawState.Drawn(
            characterList
                .value
                .shuffled()
                .take(6),
            counter.value
        )

        Log.d("CARD", _drawState.value.toString())
    }
}