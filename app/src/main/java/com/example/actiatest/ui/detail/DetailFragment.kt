package com.example.actiatest.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.actiatest.R
import com.example.actiatest.databinding.FragmentDetailBinding
import com.example.actiatest.domain.FindMoviesUseCase
import com.example.actiatest.model.MoviesRepository
import com.example.actiatest.ui.common.app
import com.example.actiatest.ui.common.launchAndCollect

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val safeArgs: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            safeArgs.movieId,
            FindMoviesUseCase(MoviesRepository(requireActivity().app))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        binding.movieDetailToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            if (state.movie != null) {
                binding.movie = state.movie
            }
        }
    }
}