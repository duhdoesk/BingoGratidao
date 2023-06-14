package com.duhdoesk.bingocoinmaster.presentation.drawer

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duhdoesk.bingocoinmaster.model.Card
import com.duhdoesk.bingocoinmaster.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DrawState {
    object Loading : DrawState()
    object Ready : DrawState()
    data class Drawn(val cardList: List<Card>) : DrawState()
}

@HiltViewModel
class DrawerViewModel @Inject constructor(private val repository: CardRepository) : ViewModel() {

//    state variables
    private val _drawState = MutableStateFlow<DrawState>(DrawState.Loading)
    val state = _drawState.asStateFlow()

//    card list
    private val cardList: MutableState<List<Card>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            cardList.value = repository.getAllCards()
            _drawState.value = DrawState.Ready
        }
    }
    fun sortNewBingoCard() {
        _drawState.value = DrawState.Drawn(
            cardList
                .value
                .shuffled()
                .subList(0, 5)
        )
    }
}