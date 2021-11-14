package me.sf.movielibrary

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.sf.movielibrary.database.MovieRoomDatabase
import me.sf.movielibrary.database.MovieRepository

class MovieApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MovieRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MovieRepository(database.movieDao()) }
}
