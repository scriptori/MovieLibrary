package me.sf.movielibrary.retrofit

import me.sf.movielibrary.database.MovieEntity
import me.sf.movielibrary.json.model.Movie
import me.sf.movielibrary.json.model.MovieSearchResponse
import me.sf.movielibrary.ui.viewmodel.MovieSearchViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class is responsible for keeping a cache of movies that has already been queried for the
 * same search criteria.
 */
class MovieSearchRequest(
    val movieSearchViewModel: MovieSearchViewModel
) : SearchRequest<MovieEntity> {
    companion object {
        const val FIRST_PAGE = 1
    }

    override var currentSearchCriteria: Pair<String, Int> = "" to 1

    override fun search(value: Pair<String, Int>) {
        if (currentSearchCriteria == value) {
            return
        }
        currentSearchCriteria = value
        val retrofit = Retrofit.Builder()
            .baseUrl(Request.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MovieSearchService::class.java)
        val call = service.getMovieSearchData(
            value.first,
            Request.API_KEY,
            value.second.toString()
        )
        call.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                searchResponse: Response<MovieSearchResponse>
            ) {
                if (searchResponse.code() == 200) {
                    searchResponse.body()?.let { mr ->
                        fetchMovie(mr.movieList, mr.totalResults)
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                movieSearchViewModel.changeValue(emptyList<MovieEntity>() to null)
            }
        })
    }

    fun fetchMovie(movies: List<MovieEntity>, totalResults: String?) {
        val movieCache = mutableListOf<Movie>()
        val retrofit = Retrofit.Builder()
            .baseUrl(Request.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MovieService::class.java)
        movies.forEach { me ->
            val call = service.getMovieData(me.imdbID, Request.API_KEY)
            call.enqueue(object : Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie>,
                    searchResponse: Response<Movie>
                ) {
                    if (searchResponse.code() == 200) {
                        searchResponse.body()?.let { m ->
                            movieCache.add(m)
                            me.plot = m.plot
                            me.director = m.director
                        }
                        if (movieCache.size == movies.size) {
                            movieSearchViewModel.changeValue(movies to totalResults)
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {}
            })
        }
    }
}
