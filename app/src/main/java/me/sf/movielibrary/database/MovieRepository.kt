package me.sf.movielibrary.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * The movie repository
 *
 * @param movieDao - The movie DAO
 */
class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: Flow<MutableList<MovieEntity>> = movieDao.getMovies()

    @WorkerThread
    suspend fun insert(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }

    @WorkerThread
    suspend fun update(movieEntity: MovieEntity) {
        movieDao.update(movieEntity)
    }

    @WorkerThread
    suspend fun delete(movieEntity: MovieEntity) {
        movieDao.delete(movieEntity)
    }

    @WorkerThread
    suspend fun deleteAll() {
        movieDao.deleteAll()
    }
}
