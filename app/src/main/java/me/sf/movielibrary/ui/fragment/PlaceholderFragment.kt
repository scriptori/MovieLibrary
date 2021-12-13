package me.sf.movielibrary.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.sf.movielibrary.database.MoviesViewModel
import me.sf.movielibrary.databinding.FragmentMainBinding
import me.sf.movielibrary.databinding.MovieFavoritesViewBinding
import me.sf.movielibrary.databinding.MovieSearchViewBinding
import me.sf.movielibrary.retrofit.MovieSearchRequest
import me.sf.movielibrary.ui.recyclerview.MovieSearchViewAdapter
import me.sf.movielibrary.ui.recyclerview.MovieViewAdapter
import me.sf.movielibrary.ui.viewmodel.MovieSearchViewModel
import me.sf.movielibrary.ui.viewmodel.PageViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment(private val moviesViewModel: MoviesViewModel) : Fragment() {
    private lateinit var pageViewModel: PageViewModel
    private lateinit var movieSearchViewModel: MovieSearchViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieSearchViewModel = ViewModelProvider(this)[MovieSearchViewModel::class.java]
        pageViewModel = ViewModelProvider(this)[PageViewModel::class.java].apply {
            changeValue(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val fl: FrameLayout = binding.sectionContainer
        pageViewModel.index.observe(viewLifecycleOwner, {
            var view: View? = null
            when (it) {
                1 -> view = getMovieSearchView()
                2 -> view = getFavoritesView()
            }
            fl.removeAllViews()
            view?.let {
                fl.addView(view)
            }
        })
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getFavoritesView(): View {
        val binding = MovieFavoritesViewBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
        binding.favoritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = MovieViewAdapter(moviesViewModel).also {
                moviesViewModel.movies.observeForever { ms ->
                    it.movieList.clear()
                    it.movieList.addAll(ms)
                    it.notifyDataSetChanged()
                }
            }
        }
        return binding.root
    }

    private fun getMovieSearchView(): View {
        val binding = MovieSearchViewBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
        val movieSearchRequest = MovieSearchRequest(movieSearchViewModel)
        binding.searchView.apply {
            // Adding text change listener to fetch the new query
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    binding.searchLabel.text = ""
                    if (query.isNotEmpty()) {
                        movieSearchRequest.search(query to MovieSearchRequest.FIRST_PAGE)
                        binding.searchView.isEnabled = false
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = MovieSearchViewAdapter(moviesViewModel).also {
                movieSearchViewModel.results.observeForever { s ->
                    it.refreshData(s)
                }
            }
        }

        return binding.root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(moviesViewModel: MoviesViewModel, sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment(moviesViewModel).apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
