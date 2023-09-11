package com.unlimit.jokes.data.remote

import com.unlimit.jokes.data.remote.model.JokeApiNetworkResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getJoke(typeOfResponse: String): Flow<JokeApiNetworkResponse>
}