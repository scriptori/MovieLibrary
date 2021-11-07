package me.sf.movielibrary.json.model

import com.google.gson.annotations.SerializedName

data class MovieSearch(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    val imdbID: String,
    @SerializedName("Type")
    val type: String = "",
    @SerializedName("Poster")
    val poster: String = ""
) {
    var isFavorite: Boolean = false
}
