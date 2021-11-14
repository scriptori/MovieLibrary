package me.sf.movielibrary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sf.movielibrary.database.MovieEntity

class MovieSearchViewModel : ViewModel() {
    var results = MutableLiveData<Pair<List<MovieEntity>, String?>>(
        emptyList<MovieEntity>() to null
    )
}
