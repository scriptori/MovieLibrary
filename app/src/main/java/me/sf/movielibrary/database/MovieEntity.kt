package me.sf.movielibrary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class MovieEntity(
    //    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @PrimaryKey var imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String = "",
    @SerializedName("Plot") var plot: String? = null,
    @SerializedName("Director") var director: String? = null,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
)
