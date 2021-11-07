package me.sf.movielibrary.retrofit

import me.sf.movielibrary.json.model.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchService {
    @GET("/")
    fun getMovieSearchData(
        @Query("s") search: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: String
    ): Call<MovieSearchResponse>
}
