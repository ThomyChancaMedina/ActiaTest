package com.example.actiatest.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.actiatest.model.database.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    if (movies != null) {
        (adapter as? MoviesAdapter)?.submitList(movies)
    }
}