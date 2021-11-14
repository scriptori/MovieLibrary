package me.sf.movielibrary.ui.recyclerview

import me.sf.movielibrary.R
import me.sf.movielibrary.database.MovieEntity

class MovieSearchViewAdapter(
    override var movieList: List<MovieEntity>
) : AbstractViewAdapter() {

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.favorite.setOnClickListener {
            val movie = movieList[position]
            movie.isFavorite = !movie.isFavorite
            it.setBackgroundResource(
                if (movie.isFavorite)
                    R.drawable.ic_favorite_24
                else
                    R.drawable.ic_favorite_border_24
            )
        }
    }
}
