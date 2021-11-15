package me.sf.movielibrary.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    val movies: LiveData<MutableList<MovieEntity>>
        get() = repository.allMovies.flowOn((Dispatchers.Main)).asLiveData(
            viewModelScope.coroutineContext
        )

    fun insert(movie: MovieEntity) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }

    fun update(movie: MovieEntity) {
        viewModelScope.launch { repository.update(movie) }
    }

    fun delete(movie: MovieEntity) {
        viewModelScope.launch { repository.delete(movie) }
    }

    fun deleteAll() {
        viewModelScope.launch { repository.deleteAll() }
    }
}
