package com.example.actiatest.ui.detail

import androidx.databinding.BindingAdapter
import com.example.actiatest.model.database.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}