package me.sf.movielibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.tabs.TabLayout
import me.sf.movielibrary.database.MovieRepository
import me.sf.movielibrary.database.MovieRoomDatabase
import me.sf.movielibrary.database.MoviesViewModel
import me.sf.movielibrary.databinding.ActivityMainBinding
import me.sf.movielibrary.ui.fragment.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val database by lazy { MovieRoomDatabase.getDatabase(this) }
    private val repository by lazy { MovieRepository(database.movieDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Fresco.initialize(applicationContext)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager,
            MoviesViewModel(repository)
        )
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}
