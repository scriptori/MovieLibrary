package me.sf.movielibrary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO: set a directory for room to use to export the schema, and set exportSchema to true
@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        /**
         * Creates the movie database singleton for the first time it is accessed and return it
         *
         * @return the movie database singleton
         */
        fun getDatabase(context: Context, scope: CoroutineScope): MovieRoomDatabase {
            // if the INSTANCE is not null, return it, otherwise create the database
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database"
                ).addCallback(MovieDatabaseCallback(scope)).build().also { INSTANCE = it }
            }
        }
    }

    private class MovieDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { d ->
                scope.launch {
                    populateDatabase(d.movieDao())
                }
            }
        }

        suspend fun populateDatabase(movieDao: MovieDao) {
            // Delete all movies
            movieDao.deleteAll()

            val movie = MovieEntity(
                "tt4834206",
                "A Series of Unfortunate Events",
                "2017–2019",
                "After the loss of their parents in a mysterious fire...",
                "https://m.media-amazon.com/images/M/MV5BMTYzMjA3OTgxOV5BMl5BanBnXkFtZTgwMjAwMDU5NjM@._V1_SX300.jpg"
                )
            movieDao.insert(movie)
        }
    }
}
