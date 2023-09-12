package com.unlimit.jokes.data

import com.unlimit.jokes.data.local.JokeHelper
import com.unlimit.jokes.data.local.entity.Joke
import com.unlimit.jokes.data.remote.ApiHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

class JokeRepositoryImpl(
    private val apiService: ApiHelper,
    private val jokeHelper: JokeHelper
) : JokeRepository {
    override suspend fun getAllTopTenJokes(noOfJokes: Int): Flow<List<Joke>> =
        jokeHelper.getJokes(noOfJokes)

    override fun fetchJokesFromApiAndStoreInToDb(typeOfResponse: String): Flow<Long> = flow {
        val jokeApiNetworkResponse = apiService.getJoke(typeOfResponse)
        jokeApiNetworkResponse.map {
            Joke(joke = it.joke, timeStamp = Calendar.getInstance().timeInMillis)
        }.collectLatest {
            jokeHelper.insertJoke(it)

        }

    }



}