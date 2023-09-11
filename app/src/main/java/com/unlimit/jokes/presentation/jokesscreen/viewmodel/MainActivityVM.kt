package com.unlimit.jokes.presentation.jokesscreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlimit.jokes.domain.GetJokeFromApiAndStoreInDbUseCase
import com.unlimit.jokes.domain.GetTopTenJokesUseCase
import com.unlimit.jokes.presentation.jokesscreen.adapter.JokeItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityVM(
    private val getJokeFromApiAndStoreInDbUseCase: GetJokeFromApiAndStoreInDbUseCase,
    private val getTopTenJokesUseCase: GetTopTenJokesUseCase
) : ViewModel() {

    private val scope = viewModelScope

    var isActivityInForeground = false
    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState

    init {
        loadUpdatedData()
    }


    private fun loadUpdatedData() {
        viewModelScope.launch {
             _uiState.value = State.Loading
            getTopTenJokesUseCase.getTopTenJokes(10)
                .collect {
                    _uiState.value = State.ShowJokes(listOfJokes = it.map {
                        JokeItem.mapToJokeItem(it)
                    })
                }
        }
    }

    fun startPeriodicDataFetching() {
        scope.launch {
            while (isActivityInForeground) {
                Log.d("Api Call", "count")
                getJokeFromApiAndStoreInDbUseCase.getJokesAndSaveInDb("json")
                delay(60_000) // Delay for 1 minute
            }
        }
    }


    sealed interface State {
        object Loading : State
        data class Error(val errorMsg: String) : State
        data class ShowJokes(val listOfJokes: List<JokeItem>) : State
    }


}