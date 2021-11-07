package me.sf.movielibrary.retrofit

import me.sf.movielibrary.json.model.MovieSearch
import me.sf.movielibrary.json.model.MovieSearchResponse
import me.sf.movielibrary.viewmodel.MovieSearchViewModel
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
        const val INITIAL_SEARCH = "2021"
        const val FIRST_PAGE = 1
    }

    override var searchCache: MutableList<MovieSearch> = mutableListOf()
    override var currentSearchCriteria: Pair<String, Int> = "" to 1

    override fun search(value: Pair<String, Int>) {
        if (value.first != currentSearchCriteria.first) {
            searchCache.clear()
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
            override fun onResponse(call: Call<MovieSearchResponse>, searchResponse: Response<MovieSearchResponse>) {
                if (searchResponse.code() == 200) {
                    searchResponse.body()?.let { mr ->
                        searchCache.addAll(mr.movieList)
                        movieSearchViewModel.results.value = searchCache to mr.totalResults
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                movieSearchViewModel.results.value = emptyList<MovieSearch>() to null
            }
        })
    }
}
