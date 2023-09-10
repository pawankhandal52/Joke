package com.unlimit.jokes.data.remote.model

import com.google.gson.annotations.SerializedName

data class JokeApiNetworkResponse(

	@field:SerializedName("joke")
	val joke: String
)
