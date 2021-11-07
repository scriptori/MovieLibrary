package me.sf.movielibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import me.sf.movielibrary.databinding.ActivityMainBinding
import me.sf.movielibrary.ext.hideKeyboard
import me.sf.movielibrary.retrofit.MovieSearchRequest
import me.sf.movielibrary.retrofit.MovieSearchRequest.Companion.FIRST_PAGE
import me.sf.movielibrary.retrofit.MovieSearchRequest.Companion.INITIAL_SEARCH
import me.sf.movielibrary.ui.recyclerview.MovieSearchViewAdapter
import me.sf.movielibrary.viewmodel.MovieSearchViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieSearchViewAdapter: MovieSearchViewAdapter
    private val movieSearchViewModel = MovieSearchViewModel()
    private val movieSearchRequest = MovieSearchRequest(movieSearchViewModel)

    private var currentCount: String? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)
        movieSearchViewAdapter = MovieSearchViewAdapter(emptyList())
        movieSearchViewModel.results.observeForever { s ->
            currentCount = s.second
            movieSearchViewAdapter.movieList = s.first
            movieSearchViewAdapter.notifyDataSetChanged()
            binding.searchView.isEnabled = true
            updateResultsLabel()
        }
        initView()
        binding.searchView.setQuery(INITIAL_SEARCH, true)
        // Request focus to prevent the keyboard to popup
        binding.searchRecyclerView.requestFocus()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding.searchView.apply {
            isIconified = false
            findViewById<ImageView>(R.id.search_close_btn).isEnabled = false
            // Adding text change listener to fetch the new query
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    updateResultsLabel()
                    if (newText.isNotEmpty()) {
                        movieSearchRequest.search(newText to FIRST_PAGE)
                        binding.searchView.isEnabled = false
                    }
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
            setOnTouchListener { v, _ ->
                v.requestFocus()
                hideKeyboard()
                false
            }
        }
    }

    private fun updateResultsLabel() {
        binding.searchLabel.text = if (!binding.searchView.query.isNullOrEmpty())
            resources.getString(R.string.search_label, INITIAL_SEARCH, currentCount ?: "0") else ""
    }
}
