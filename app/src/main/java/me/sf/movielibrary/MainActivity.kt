package me.sf.movielibrary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.sf.movielibrary.databinding.ActivityMainBinding
import me.sf.movielibrary.ext.hideKeyboard
import me.sf.movielibrary.ui.recyclerview.MovieSearchViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieSearchViewAdapter: MovieSearchViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)
        movieSearchViewAdapter = MovieSearchViewAdapter(emptyList())
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding.searchView.apply {
            isIconified = false
            findViewById<ImageView>(R.id.search_close_btn).isEnabled = false
        }
        binding.searchLabel.text = resources.getString(R.string.search_label, "2021")
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = movieSearchViewAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(!binding.searchRecyclerView.canScrollVertically(1)) {
                        // Fetch more movies when hitting the bottom of this page
                    }
                }
            })
            setOnTouchListener { v, _ ->
                v.requestFocus()
                hideKeyboard()
                false
            }
        }
        // Request focus to prevent the keyboard to popup
        binding.searchRecyclerView.requestFocus()
    }
}
