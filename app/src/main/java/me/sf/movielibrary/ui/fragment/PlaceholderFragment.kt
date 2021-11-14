package me.sf.movielibrary.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.sf.movielibrary.MovieApplication
import me.sf.movielibrary.database.MovieRepository
import me.sf.movielibrary.database.MoviesViewModel
import me.sf.movielibrary.ui.controller.MovieSearchViewController
import me.sf.movielibrary.databinding.FragmentMainBinding
import me.sf.movielibrary.ui.controller.FavoritesMovieViewController
import me.sf.movielibrary.ui.viewmodel.MovieSearchViewModel
import me.sf.movielibrary.ui.viewmodel.PageViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {
    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val movieSearchViewModel = MovieSearchViewModel()
    private lateinit var repository: MovieRepository
    private lateinit var moviesViewModel: MoviesViewModel

    init {
        movieSearchViewModel.results.observe(this) { r ->
            moviesViewModel.deleteAll()
            r.first.filter { it.isFavorite }.forEach { m ->
                moviesViewModel.insert(m)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            index.value = (arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        repository = (requireActivity().application as MovieApplication).repository
        moviesViewModel = MoviesViewModel(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val searchViewController = MovieSearchViewController(
            this.requireContext(),
            viewLifecycleOwner,
            movieSearchViewModel
        )
        val favoritesMovieViewController = FavoritesMovieViewController(
            this.requireContext(),
            viewLifecycleOwner,
            moviesViewModel
        )

        val fl: FrameLayout = binding.sectionContainer
        pageViewModel.index.observe(viewLifecycleOwner, {
            var view: View? = null
            when (it) {
                1 -> view = searchViewController.binding.root
                2 -> view = favoritesMovieViewController.binding.root
            }
            fl.removeAllViews()
            view?.let {
                fl.addView(view)
            }
        })
        return root
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
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
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
