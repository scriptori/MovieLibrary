package me.sf.movielibrary.ui.controller

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import me.sf.movielibrary.database.MoviesViewModel
import me.sf.movielibrary.databinding.MovieFavoritesViewBinding
import me.sf.movielibrary.ui.recyclerview.MovieViewAdapter

class FavoritesMovieViewController(
    context: Context,
    viewLifecycleOwner: LifecycleOwner,
    movies: MoviesViewModel
) {
    var binding = MovieFavoritesViewBinding.inflate(LayoutInflater.from(context), null, false)
    private var adapter = MovieViewAdapter(movies)

    init {
        movies.allMovies.observe(viewLifecycleOwner) {

        }
    }
}
