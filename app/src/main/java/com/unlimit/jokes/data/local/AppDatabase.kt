package com.unlimit.jokes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unlimit.jokes.data.local.dao.JokeDao
import com.unlimit.jokes.data.local.entity.Joke

@Database(entities = [Joke::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jokeDao(): JokeDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "joke_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

    }


}