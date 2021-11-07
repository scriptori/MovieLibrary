package me.sf.movielibrary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.sf.movielibrary.json.model.MovieSearch

class MovieSearchViewModel : ViewModel() {
    var results = MutableLiveData<Pair<List<MovieSearch>, String?>>(emptyList<MovieSearch>() to null)
}
