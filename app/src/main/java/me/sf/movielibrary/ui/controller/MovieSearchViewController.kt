package me.sf.movielibrary.ui.controller

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.sf.movielibrary.database.MoviesViewModel
import me.sf.movielibrary.databinding.MovieSearchViewBinding
import me.sf.movielibrary.retrofit.MovieSearchRequest
import me.sf.movielibrary.ui.recyclerview.MovieSearchViewAdapter
import me.sf.movielibrary.ui.viewmodel.MovieSearchViewModel

@SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
class MovieSearchViewController(
    context: Context,
    viewLifecycleOwner: LifecycleOwner,
    movieSearchViewModel: MovieSearchViewModel,
    moviesViewModel: MoviesViewModel
) {

}
