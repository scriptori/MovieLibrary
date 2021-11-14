package me.sf.movielibrary.ui.recyclerview

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.sf.movielibrary.R
import me.sf.movielibrary.database.MovieEntity
import me.sf.movielibrary.databinding.RecyclerviewItemBinding

abstract class AbstractViewAdapter :
    RecyclerView.Adapter<AbstractViewAdapter.MovieSearchViewHolder>() {
    companion object {
        private const val NA = "N/A"
    }

    abstract val movieList: List<MovieEntity>

    internal lateinit var context: Context
    internal lateinit var resources: Resources

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        resources = parent.resources
        context = parent.context
        val binding = RecyclerviewItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return MovieSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        val movieSearch = movieList[position]
        holder.binding.apply {
            title.text = resources.getString(R.string.movie_title_label, movieSearch.title)
            year.text = resources.getString(R.string.movie_year_label, movieSearch.year)
            director.text = resources.getString(
                R.string.movie_director_label,
                movieSearch.director ?: NA
            )
            plot.text = resources.getString(R.string.movie_plot_label, movieSearch.plot
            )
            poster.setImageURI(movieSearch.poster)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieSearchViewHolder(binding: RecyclerviewItemBinding) :
        BindingViewHolder<RecyclerviewItemBinding>(binding)

}
