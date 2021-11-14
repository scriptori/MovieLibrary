package me.sf.movielibrary.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class MovieEntity(
    var imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String = "",
    @SerializedName("Plot") var plot: String = "",
    @SerializedName("Director") var director: String? = null
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    var isFavorite: Boolean = false
}
