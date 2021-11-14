package me.sf.movielibrary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sf.movielibrary.json.model.Movie

class MovieViewModel : ViewModel() {
    var results = MutableLiveData<List<Movie>>(
        emptyList<Movie>()
    )
}
