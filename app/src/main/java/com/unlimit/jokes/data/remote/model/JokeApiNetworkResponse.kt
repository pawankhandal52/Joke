package com.unlimit.jokes.data.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class JokeApiNetworkResponse(

    @field:SerializedName("joke")
    val joke: String
)
