package me.sf.movielibrary

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File
import me.sf.movielibrary.json.model.Movie
import me.sf.movielibrary.json.model.MovieSearchResponse
import me.sf.movielibrary.util.JsonUtil

class MovieDataTest {
    @Test
    fun `test if json content can be converted to movie data class`() {
        // content from "http://www.omdbapi.com/?i=tt3896198&apikey=2110d285"
        val movie = JsonUtil.fromJsonFile(File(
            "src/test/assets/movie.json"),
            Movie::class.java
        )
        assertEquals("A Series of Unfortunate Events", movie.title)
        assertEquals("N/A", movie.director)
        assertNotNull(movie.plot)
        assertNotNull(movie.poster)
        assertEquals("tt4834206", movie.imdbID)
        assertEquals("2017–2019", movie.year)
    }

    @Test
    fun `test if json content can be converted to search data class`() {
        val searchResponse = JsonUtil.fromJsonFile(
            File("src/test/assets/search.json"),
            MovieSearchResponse::class.java
        )
        val movie = searchResponse.movieList[0]
        assertEquals(470, searchResponse.totalResults?.toInt())
        assertEquals("Batman: Hush", movie.title)
        assertEquals("2019", movie.year)
        assertNotNull(movie.poster)
        assertEquals("tt8752440", movie.imdbID)
        assertEquals(10, searchResponse.movieList.size)
    }
}
