package com.unlimit.jokes.data.local

import com.unlimit.jokes.data.local.entity.Joke
import kotlinx.coroutines.flow.Flow

interface JokeHelper {
    suspend fun insertJoke(joke: Joke)
    suspend fun getJokes(noOfJokes:Int):Flow<List<Joke>>
}