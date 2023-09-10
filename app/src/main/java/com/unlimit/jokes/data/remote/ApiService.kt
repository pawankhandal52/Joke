package com.unlimit.jokes.data.remote

import com.unlimit.jokes.data.remote.model.JokeApiNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET("api")
    suspend fun getJokes(@Query("format") typeOfResponse: String): JokeApiNetworkResponse
}