package com.unlimit.jokes.domain

import android.util.Log
import com.unlimit.jokes.data.JokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn

class GetJokeFromApiAndStoreInDbUseCase(private val jokeRepository: JokeRepository) {
    private val TAG = GetJokeFromApiAndStoreInDbUseCase::class.simpleName
    suspend fun getJokesAndSaveInDb(typeOfResponse: String) {
        jokeRepository.fetchJokesFromApiAndStoreInToDb(typeOfResponse)
            .flowOn(Dispatchers.IO)
            .catch {
                Log.d(TAG, it.message!!)
            }.collectLatest {
                Log.d(TAG, it.toString())
            }

    }
}