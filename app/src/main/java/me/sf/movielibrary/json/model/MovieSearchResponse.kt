package me.sf.movielibrary.json.model

import com.google.gson.annotations.SerializedName

class MovieSearchResponse {
    @SerializedName("Search")
    val movieList: List<MovieSearch> = emptyList()
    @SerializedName("totalResults")
    val totalResults: String? = null
}
