package me.sf.movielibrary.ui.controller

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.sf.movielibrary.databinding.MovieSearchViewBinding
import me.sf.movielibrary.retrofit.MovieSearchRequest
import me.sf.movielibrary.ui.recyclerview.MovieSearchViewAdapter
import me.sf.movielibrary.ui.viewmodel.MovieSearchViewModel

@SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
class MovieSearchViewController(context: Context) {

    var binding: MovieSearchViewBinding =
        MovieSearchViewBinding.inflate(LayoutInflater.from(context), null, false)
    private var movieSearchViewAdapter: MovieSearchViewAdapter = MovieSearchViewAdapter(emptyList())
    private val movieSearchViewModel = MovieSearchViewModel()
    private val movieSearchRequest = MovieSearchRequest(movieSearchViewModel)

    private var currentCount: String? = null

    init {
        movieSearchViewModel.results.observeForever { s ->
            currentCount = s.second
            movieSearchViewAdapter.movieList = s.first
            movieSearchViewAdapter.notifyDataSetChanged()
            binding.searchView.isEnabled = true
            binding.searchLabel.text = ""
        }

        binding.searchView.apply {
            // Adding text change listener to fetch the new query
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    binding.searchLabel.text = ""
                    if (query.isNotEmpty()) {
                        movieSearchRequest.search(query to MovieSearchRequest.FIRST_PAGE)
                        binding.searchView.isEnabled = false
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = movieSearchViewAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!binding.searchRecyclerView.canScrollVertically(1)) {
                        // Fetch more movies when hitting the bottom of this page
                        movieSearchRequest.apply {
                            search(currentSearchCriteria.first to currentSearchCriteria.second + 1)
                        }
                    }
                }
            })
        }
    }
}
