package com.unlimit.jokes.data.local.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "joke")
data class Joke(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val joke: String,
    @ColumnInfo("time_stamp") val timeStamp: Long
)
