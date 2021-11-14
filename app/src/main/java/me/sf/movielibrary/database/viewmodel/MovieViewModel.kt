package me.sf.movielibrary.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.sf.movielibrary.database.entity.MovieEntity
import me.sf.movielibrary.database.repository.MovieRepository

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    val allMovies: LiveData<List<MovieEntity>> = repository.allMovies.asLiveData(
        viewModelScope.coroutineContext
    )

    /**
     * Launch a coroutine to insert the movie data in a non-blocking way
     */
    fun insert(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.insert(movieEntity)
    }

    fun delete(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.delete(movieEntity)
    }

    fun update(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.update(movieEntity)
    }

    fun clean() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
