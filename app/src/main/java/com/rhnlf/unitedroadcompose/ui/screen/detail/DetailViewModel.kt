package com.rhnlf.unitedroadcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhnlf.unitedroadcompose.data.PlayerRepository
import com.rhnlf.unitedroadcompose.model.Player
import com.rhnlf.unitedroadcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PlayerRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Player>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Player>>
        get() = _uiState

    fun getPlayerById(playerId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getPlayerById(playerId))
        }
    }
}