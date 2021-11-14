package me.sf.movielibrary.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.sf.movielibrary.database.entity.MovieEntity

@Dao
interface MovieDao {
    /**
     * Execute this query on a separate thread and notify the observer when the data has changed
     */
    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Update
    suspend fun update(movieEntity: MovieEntity)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}
