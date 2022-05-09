package com.example.actiatest.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.actiatest.R
import com.example.actiatest.databinding.ListMoviesFindBinding
import com.example.actiatest.model.database.Movie

class MovieSearchAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieSearchAdapter.ViewHolder>() {

    var movie: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_movies_find, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movie[position]
        Log.i("TAG", "onBindViewHolder: thomy::::"+movie.title)
        holder.bindItems(movie)
        holder.itemView.setOnClickListener { listener(movie) }

    }

    override fun getItemCount(): Int = movie.size



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val viewBinding = ListMoviesFindBinding.bind(itemView)
        fun bindItems(movie: Movie) {

            with(viewBinding) {
                textViewUsername.text = movie.title
                textViewAge.text = movie.releaseDate
            }

            itemView.setOnClickListener({
                Toast.makeText(itemView.context, "His name is " + movie.title, Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }


}