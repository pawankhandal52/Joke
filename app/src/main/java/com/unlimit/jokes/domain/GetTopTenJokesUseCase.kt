package com.unlimit.jokes.domain

import android.util.Log
import com.unlimit.jokes.data.JokeRepositoryImpl
import com.unlimit.jokes.data.local.entity.Joke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class GetTopTenJokesUseCase(private val jokeRepositoryImpl: JokeRepositoryImpl) {
    private val TAG = GetTopTenJokesUseCase::class.java.simpleName
    suspend fun getTopTenJokes(noOfJokes: Int): Flow<List<Joke>> {
        return jokeRepositoryImpl.getAllTopTenJokes(noOfJokes)
            .flowOn(Dispatchers.IO)
            .catch {
                Log.d(TAG, it.message!!)
            }
    }
}