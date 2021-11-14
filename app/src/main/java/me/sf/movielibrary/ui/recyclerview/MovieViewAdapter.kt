package me.sf.movielibrary.ui.recyclerview

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.sf.movielibrary.R
import me.sf.movielibrary.database.MovieEntity
import me.sf.movielibrary.database.MoviesViewModel

class MovieViewAdapter(
    private var moviesViewModel: MoviesViewModel
) : AbstractViewAdapter() {
    override val movieList: List<MovieEntity>
        get() = moviesViewModel.allMovies.value!!

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.favorite.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setMessage(resources.getString(R.string.favorites_delete_confirmation))
                .setNegativeButton(resources.getString(R.string.cancel)) { d, _ ->
                    d.cancel()
                }
                .setPositiveButton(resources.getString(R.string.unfavorite)) { d, _ ->
                    it.setBackgroundResource(R.drawable.ic_favorite_border_24)
                    moviesViewModel.delete(movieList[position])
                    d.dismiss()
                }
                .show()
        }
    }
}
