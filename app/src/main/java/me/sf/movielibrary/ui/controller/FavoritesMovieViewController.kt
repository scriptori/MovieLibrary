package me.sf.movielibrary.ui.controller

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import me.sf.movielibrary.MovieApplication
import me.sf.movielibrary.databinding.MovieFavoritesViewBinding
import me.sf.movielibrary.ui.recyclerview.MovieSearchViewAdapter

class FavoritesMovieViewController(
    context: Context,
    viewLifecycleOwner: LifecycleOwner
) {
    var binding = MovieFavoritesViewBinding.inflate(LayoutInflater.from(context), null, false)
    private val repository = (context.applicationContext as MovieApplication).repository
    private val allMovies = repository.allMovies.flowOn(Dispatchers.Main).asLiveData()
    private var adapter = MovieSearchViewAdapter(emptyList())
    init {
        allMovies.observe(viewLifecycleOwner) { movies ->
            adapter.movieList = movies
        }
    }
}
