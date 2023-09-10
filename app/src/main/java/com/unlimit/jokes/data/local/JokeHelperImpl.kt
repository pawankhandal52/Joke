package com.unlimit.jokes.data.local

import com.unlimit.jokes.data.local.entity.Joke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JokeHelperImpl(private val appDatabase: AppDatabase): JokeHelper {

    override suspend fun insertJoke(joke: Joke){
        appDatabase.jokeDao().insert(joke)
    }

    override suspend fun getJokes(noOfJokes: Int): Flow<List<Joke>> {
        return appDatabase.jokeDao().getJokesOfTheDay(noOfJokes)
    }

}