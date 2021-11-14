package me.sf.movielibrary.json.model

import com.google.gson.annotations.SerializedName
import me.sf.movielibrary.database.MovieEntity

class MovieSearchResponse {
    @SerializedName("Search")
    val movieList: List<MovieEntity> = emptyList()
    @SerializedName("totalResults")
    val totalResults: String? = null
}
