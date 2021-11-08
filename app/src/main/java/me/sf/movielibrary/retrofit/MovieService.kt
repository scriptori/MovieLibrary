package me.sf.movielibrary.retrofit

import me.sf.movielibrary.json.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("/")
    fun getMovieData(
        @Query("i") movieId: String,
        @Query("apikey") apiKey: String
    ): Call<Movie>
}
