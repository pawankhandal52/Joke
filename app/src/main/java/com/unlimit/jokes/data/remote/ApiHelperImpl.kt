package com.unlimit.jokes.data.remote

import com.unlimit.jokes.data.remote.model.JokeApiNetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService):ApiHelper {
    override fun getJoke(typeOfResponse:String): Flow<JokeApiNetworkResponse> = flow {
        emit(apiService.getJokes(typeOfResponse))
    }
}