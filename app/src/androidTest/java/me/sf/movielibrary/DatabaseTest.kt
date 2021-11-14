package me.sf.movielibrary

import androidx.lifecycle.asLiveData
import org.junit.Assert
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class DatabaseTest : InstrumentationTest() {
    @Test
    fun databaseTest() {
        val repository = (appContext.applicationContext as MovieApplication).repository
        Assert.assertNotNull(repository)
        repository.allMovies.flowOn(Dispatchers.Main).asLiveData().value
    }
}
