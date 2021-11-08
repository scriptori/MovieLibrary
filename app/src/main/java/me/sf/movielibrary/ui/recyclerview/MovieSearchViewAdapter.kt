package me.sf.movielibrary.ui.recyclerview

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.sf.movielibrary.R
import me.sf.movielibrary.databinding.RecyclerviewSearchItemBinding
import me.sf.movielibrary.json.model.MovieSearch

class MovieSearchViewAdapter(
    internal var movieList: List<MovieSearch>
) : RecyclerView.Adapter<MovieSearchViewAdapter.MovieSearchViewHolder>() {
    companion object {
        private const val NA = "N/A"
    }
    private lateinit var resources: Resources

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        resources = parent.resources
        val binding = RecyclerviewSearchItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
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
            plot.text = resources.getString(
                R.string.movie_plot_label,
                movieSearch.plot
            )
            poster.setImageURI(movieSearch.poster)
        }
    }

    inner class MovieSearchViewHolder(binding: RecyclerviewSearchItemBinding) :
        BindingViewHolder<RecyclerviewSearchItemBinding>(binding)

    override fun getItemCount(): Int {
        return movieList.size
    }
}
