package me.sf.movielibrary.ui.recyclerview

import me.sf.movielibrary.R
import me.sf.movielibrary.database.MovieEntity
import me.sf.movielibrary.database.MoviesViewModel

class MovieSearchViewAdapter(
    private val moviesViewModel: MoviesViewModel
) : AbstractViewAdapter() {
    private var currentCount: String? = null

    override val movieList: MutableList<MovieEntity> = mutableListOf()

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.favorite.setOnClickListener {
            val movie = movieList[position]
            movie.isFavorite = !movie.isFavorite
            if (movie.isFavorite) {
                it.setBackgroundResource(R.drawable.ic_favorite_24)
                moviesViewModel.insert(movie)
                moviesViewModel.movies
            } else {
                it.setBackgroundResource(R.drawable.ic_favorite_border_24)
                moviesViewModel.delete(movie)
                moviesViewModel.movies
            }
        }
    }

    internal fun refreshData(value: Pair<List<MovieEntity>, String?>) {
        currentCount = value.second
        movieList.clear()
        value.first.forEachIndexed { index, movieEntity ->
            movieList.add(index, movieEntity)
            notifyItemInserted(index)
        }
    }
}
