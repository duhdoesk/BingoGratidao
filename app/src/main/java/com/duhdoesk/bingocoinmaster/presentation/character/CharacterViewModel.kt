package com.duhdoesk.bingocoinmaster.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duhdoesk.bingocoinmaster.model.Card
import com.duhdoesk.bingocoinmaster.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CharacterState {
    object Loading : CharacterState()
    data class Ready(val card: Card) : CharacterState()
}

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: CardRepository) : ViewModel() {

    //    state variables
    private val _charState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val state = _charState.asStateFlow()

    fun getCharacterById(charId: String) {
        viewModelScope.launch {
            repository.getCardById(charId)?.let {
                _charState.value = CharacterState.Ready(it)
            }
        }
    }
}