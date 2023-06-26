package com.duhdoesk.bingocoinmaster.presentation.card

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

sealed class CardDrawState {
    object Loading : CardDrawState()
    object Ready : CardDrawState()
    data class Drawn(val characterList: List<Character>, val counter: Int) : CardDrawState()
}

@HiltViewModel
class CardViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

//    state variables
    private val _Card_drawState = MutableStateFlow<CardDrawState>(CardDrawState.Loading)
    val state = _Card_drawState.asStateFlow()

//    card list
    private val characterList: MutableState<List<Character>> = mutableStateOf(emptyList())

//    card counter
    private val counter = mutableStateOf(0)

    init {
        viewModelScope.launch {
            characterList.value = repository.getAllCharacters()
            _Card_drawState.value = CardDrawState.Ready
        }
    }
    fun sortNewBingoCard() {
        counter.value += 1

        _Card_drawState.value = CardDrawState.Drawn(
            characterList
                .value
                .shuffled()
                .take(6),
            counter.value
        )

        Log.d("CARD", _Card_drawState.value.toString())
    }
}