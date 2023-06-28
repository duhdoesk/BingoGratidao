package com.duhdoesk.bingocoinmaster.presentation.drawer

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

sealed class DrawerState() {
    object Loading : DrawerState()
    data class Drawing(val drawnCharacters: List<Character>) : DrawerState()
}

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    //    state variables
    private val _drawState = MutableStateFlow<DrawerState>(DrawerState.Loading)
    val state = _drawState.asStateFlow()

}