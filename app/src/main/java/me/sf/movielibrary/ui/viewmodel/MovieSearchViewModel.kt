package me.sf.movielibrary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sf.movielibrary.json.model.MovieSearch

class MovieSearchViewModel : ViewModel() {
    var results = MutableLiveData<Pair<List<MovieSearch>, String?>>(emptyList<MovieSearch>() to null)
}
