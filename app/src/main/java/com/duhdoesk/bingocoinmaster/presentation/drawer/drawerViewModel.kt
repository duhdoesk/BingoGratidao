package com.duhdoesk.bingocoinmaster.presentation.drawer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duhdoesk.bingocoinmaster.model.Card
import com.duhdoesk.bingocoinmaster.repository.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DrawState {
    object Loading : DrawState()
    data class Ready(val cardList: List<Card>) : DrawState()
}

class drawerViewModel @Inject constructor(private val repository: CardRepository) : ViewModel() {

//    state variables
    private val _drawState = MutableStateFlow<DrawState>(DrawState.Loading)
    val state = _drawState.asStateFlow()

//    card list
    private val cardList: MutableState<List<Card>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cardList.value = repository.getAllCards()
            sortNewBingoCard()
        }
    }

    fun sortNewBingoCard() {
        _drawState.value = DrawState.Ready(
            cardList
                .value
                .shuffled()
                .subList(0, 5)
        )
    }
}