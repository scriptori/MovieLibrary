package me.sf.movielibrary.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.sf.movielibrary.databinding.RecyclerviewSearchItemBinding
import me.sf.movielibrary.json.model.MovieSearch

class MovieSearchViewAdapter(
    internal var movieList: List<MovieSearch>
) : RecyclerView.Adapter<MovieSearchViewAdapter.MovieSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        val binding = RecyclerviewSearchItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        val movieSearch = movieList[position]
        holder.binding.apply {
            title.text = movieSearch.title
            year.text = movieSearch.year
            poster.setImageURI(movieSearch.poster)
        }
    }

    inner class MovieSearchViewHolder(binding: RecyclerviewSearchItemBinding) :
        BindingViewHolder<RecyclerviewSearchItemBinding>(binding)

    override fun getItemCount(): Int {
        return movieList.size
    }
}
