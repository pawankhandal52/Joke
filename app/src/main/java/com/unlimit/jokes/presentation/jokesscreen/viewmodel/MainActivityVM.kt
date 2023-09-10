package com.unlimit.jokes.presentation.jokesscreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unlimit.jokes.data.JokeRepository
import com.unlimit.jokes.data.local.JokeHelperImpl
import com.unlimit.jokes.data.local.entity.Joke
import com.unlimit.jokes.domain.GetJokeFromApiAndStoreInDbUseCase
import com.unlimit.jokes.domain.GetTopTenJokesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivityVM(
    private val getJokeFromApiAndStoreInDbUseCase: GetJokeFromApiAndStoreInDbUseCase,
    private val getTopTenJokesUseCase: GetTopTenJokesUseCase,
    private val repository: JokeRepository,
    private val jokeHelperImpl: JokeHelperImpl
):ViewModel() {

    private val scope = viewModelScope


    private val _uiState = MutableStateFlow<State>(State.Loading)

    val uiState: StateFlow<State> = _uiState

    init {
        startPeriodicDataFetching()
        //loadDataIntoDb()
        loadUpdatedData()
    }

    fun loadDataIntoDb() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveDatainDb(Joke(joke ="test"))
        }
    }

    fun loadUpdatedData(){
        viewModelScope.launch {
            getTopTenJokesUseCase.getTopTenJokes(10)
                .collect {
                    _uiState.value = State.ShowJokes(listOfJokes = it)
                }
        }
    }

    private fun startPeriodicDataFetching() {
        scope.launch {
            while (true) {
                Log.d("Api Call","count")
                getJokeFromApiAndStoreInDbUseCase.getJokesAndSaveInDb("json")
                delay(60_000) // Delay for 1 minute
            }
        }
    }

    fun cancelDataFetching() {
        viewModelScope.cancel() // Cancel the entire scope and all its coroutines
    }

    sealed interface State{
        object Loading:State
        data class Error(val errorMsg:String):State
        data class ShowJokes(val listOfJokes:List<Joke>):State
    }



}