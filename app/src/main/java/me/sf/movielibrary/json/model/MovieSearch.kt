package me.sf.movielibrary.json.model

import com.google.gson.annotations.SerializedName

data class MovieSearch(
    val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String = "",
    @SerializedName("Plot") var plot: String = "",
    @SerializedName("Director") var director: String = ""
) {
    var isFavorite: Boolean = false
}
