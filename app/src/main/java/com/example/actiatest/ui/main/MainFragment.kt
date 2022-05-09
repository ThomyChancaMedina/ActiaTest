package com.example.actiatest.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.actiatest.R
import com.example.actiatest.databinding.FragmentMainBinding
import com.example.actiatest.domain.GetPopularMoviesUseCase
import com.example.actiatest.domain.RequestPopularMoviesUseCase
import com.example.actiatest.model.MoviesRepository
import com.example.actiatest.model.database.Movie
import com.example.actiatest.ui.common.MovieSearchAdapter
import com.example.actiatest.ui.common.app
import com.example.actiatest.ui.common.launchAndCollect

class MainFragment : Fragment(R.layout.fragment_main), SearchView.OnQueryTextListener {
    val filteredMovies = ArrayList<Movie>()
    private lateinit var adapterSearch: MovieSearchAdapter

    var movieList: List<Movie>? = null

    var movies = ArrayList<Movie>()
    private val viewModel: MainViewModel by viewModels {
        val repository = MoviesRepository(requireActivity().app)
        MainViewModelFactory(
            GetPopularMoviesUseCase(repository),
            RequestPopularMoviesUseCase(repository)
        )
    }
    private var binding: FragmentMainBinding? = null

    private lateinit var mainState: MainState

    private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState()

        binding = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding!!.loading = it.loading
            binding!!.movies = it.movies
            movieList = it.movies
            binding!!.error = it.error?.let(mainState::errorToString)
        }

        mainState.requestLocationPermission {
            viewModel.onUiReady()
        }

        binding!!.recyclerSearch.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL, false
        )

        adapterSearch = MovieSearchAdapter { mainState.onMovieClicked(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.menu, menu)

        val searchViewItem = menu.findItem(R.id.menuSearch).actionView as SearchView
        searchViewItem.isSubmitButtonEnabled = false
        searchViewItem.setOnQueryTextListener(this)
        searchViewItem.maxWidth = Int.MAX_VALUE

        searchViewItem.queryHint = "Search View Hint"

    }


    override fun onQueryTextChange(newText: String): Boolean {
        searchNote(newText)
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchNote(query: String) {

        if (query.isEmpty()) {
            filteredMovies.clear()
            binding!!.recyclerSearch.adapter!!.notifyDataSetChanged()

        } else {
            filteredMovies.clear()
            for (movie in movieList!!) {
                if (movie.title.toLowerCase().contains(query.toLowerCase())) {
                    filteredMovies.add(movie)
                }
            }
            if (filteredMovies.isEmpty()) {
                binding!!.tvEmpty.visibility = View.VISIBLE
            }
            adapterSearch.movie = filteredMovies
            binding!!.recyclerSearch.adapter = adapterSearch
        }


    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false

    }
}