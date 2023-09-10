package com.unlimit.jokes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unlimit.jokes.data.local.entity.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joke: Joke): Long

    @Query("SELECT * FROM joke ORDER BY id DESC LIMIT :limit")
    fun getJokesOfTheDay(limit:Int): Flow<List<Joke>>
}