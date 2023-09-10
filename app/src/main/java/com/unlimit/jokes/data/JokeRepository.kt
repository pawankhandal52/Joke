package com.unlimit.jokes.data

import com.unlimit.jokes.data.local.entity.Joke
import kotlinx.coroutines.flow.Flow
import retrofit2.http.QueryMap

interface JokeRepository {

    suspend fun getAllTopTenJokes(noOfJokes:Int):Flow<List<Joke>>

    fun fetchJokesFromApiAndStoreInToDb(typeOfResponse:String):Flow<Long>

    suspend fun saveDatainDb(joke: Joke)

}