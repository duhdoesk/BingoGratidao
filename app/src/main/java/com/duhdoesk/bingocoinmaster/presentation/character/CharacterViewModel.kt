package com.duhdoesk.bingocoinmaster.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duhdoesk.bingocoinmaster.model.Character
import com.duhdoesk.bingocoinmaster.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CharacterState {
    object Loading : CharacterState()
    data class Ready(val character: Character) : CharacterState()
}

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

    //    state variables
    private val _charState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val state = _charState.asStateFlow()

    fun getCharacterById(charId: String) {
        viewModelScope.launch {
            repository.getCharacterById(charId)?.let {
                _charState.value = CharacterState.Ready(it)
            }
        }
    }
}