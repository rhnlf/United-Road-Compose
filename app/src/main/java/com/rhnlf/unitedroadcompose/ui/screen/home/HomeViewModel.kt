package com.rhnlf.unitedroadcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhnlf.unitedroadcompose.data.PlayerRepository
import com.rhnlf.unitedroadcompose.model.Player
import com.rhnlf.unitedroadcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PlayerRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Player>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Player>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllPlayers() {
        viewModelScope.launch {
            repository.getPlayers().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { players ->
                _uiState.value = UiState.Success(players)
            }
        }
    }

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchPlayers(_query.value).catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { players ->
                _uiState.value = UiState.Success(players)
            }
        }
    }
}

