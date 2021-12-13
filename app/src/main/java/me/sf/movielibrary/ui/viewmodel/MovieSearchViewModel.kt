package me.sf.movielibrary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sf.movielibrary.database.MovieEntity

class MovieSearchViewModel : ViewModel() {
    private val _results = MutableLiveData<Pair<List<MovieEntity>, String?>>(
        emptyList<MovieEntity>() to null
    )
    val results: LiveData<Pair<List<MovieEntity>, String?>>
        get() = _results

    fun changeValue(v: Pair<List<MovieEntity>, String?>) {
        _results.value = v
    }
}
