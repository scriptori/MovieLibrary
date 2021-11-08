package me.sf.movielibrary.retrofit

import me.sf.movielibrary.json.model.Movie
import me.sf.movielibrary.json.model.MovieSearch
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
) : SearchRequest<MovieSearch> {
    companion object {
        const val FIRST_PAGE = 1
    }

    var movieCache = mutableListOf<Movie>()
    override var totalResults: Int? = null
    override var searchCache: MutableList<MovieSearch> = mutableListOf()
    override var currentSearchCriteria: Pair<String, Int> = "" to 1

    override fun search(value: Pair<String, Int>) {
        if (value.first != currentSearchCriteria.first) {
            searchCache.clear()
            movieCache.clear()
            totalResults = null
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
                        mr.movieList.forEach { cacheMovie(it.imdbID) }
                        searchCache.addAll(mr.movieList)
                        totalResults = mr.totalResults?.toInt()
                    }
                } else {
                    movieSearchViewModel.results.value = emptyList<MovieSearch>() to null
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                movieSearchViewModel.results.value = emptyList<MovieSearch>() to null
            }
        })
    }

    fun cacheMovie(moveId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Request.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MovieService::class.java)
        val call = service.getMovieData(moveId, Request.API_KEY)
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(
                call: Call<Movie>,
                searchResponse: Response<Movie>
            ) {
                if (searchResponse.code() == 200) {
                    searchResponse.body()?.let { m ->
                        movieCache.add(m)
                        searchCache.find { m.imdbID == it.imdbID }?.apply {
                            plot = m.plot
                            director = m.director
                        }
                        if (movieCache.size == searchCache.size) {
                            movieSearchViewModel.results.value =
                                searchCache to totalResults?.toString()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {}
        })
    }
}
