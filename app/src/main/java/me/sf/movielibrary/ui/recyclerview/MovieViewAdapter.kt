package me.sf.movielibrary.ui.recyclerview

import android.annotation.SuppressLint
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.sf.movielibrary.R
import me.sf.movielibrary.database.MovieEntity
import me.sf.movielibrary.database.MoviesViewModel

@SuppressLint("NotifyDataSetChanged")
class MovieViewAdapter(
    private val moviesViewModel: MoviesViewModel
) : AbstractViewAdapter() {
    override val movieList = mutableListOf<MovieEntity>()

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.favorite.apply {
            setBackgroundResource(R.drawable.ic_favorite_24)
            setOnClickListener {
                MaterialAlertDialogBuilder(context)
                    .setMessage(resources.getString(R.string.favorites_delete_confirmation))
                    .setNegativeButton(resources.getString(R.string.no_label)) { d, _ ->
                        d.cancel()
                    }
                    .setPositiveButton(resources.getString(R.string.yes_label)) { d, _ ->
                        val me = movieList[position]
                        moviesViewModel.delete(me)
                        movieList.remove(me)
                        notifyItemRemoved(position)
                        d.dismiss()
                    }
                    .show()
            }
        }
    }
}
