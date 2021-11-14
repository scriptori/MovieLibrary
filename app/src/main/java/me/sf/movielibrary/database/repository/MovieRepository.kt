package me.sf.movielibrary.database.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import me.sf.movielibrary.database.dao.MovieDao
import me.sf.movielibrary.database.entity.MovieEntity

/**
 * The movie repository
 *
 * @param movieDao - The movie DAO
 */
class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: Flow<List<MovieEntity>> = movieDao.getMovies()

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
