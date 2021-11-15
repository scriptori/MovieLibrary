package me.sf.movielibrary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
        fun getDatabase(context: Context): MovieRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database"
                ).addCallback(MovieDatabaseCallback()).build().also { INSTANCE = it }
            }
        }
    }

    private class MovieDatabaseCallback : RoomDatabase.Callback()
}
