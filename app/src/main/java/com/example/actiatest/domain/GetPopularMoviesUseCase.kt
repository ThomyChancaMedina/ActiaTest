package com.example.actiatest.domain

import com.example.actiatest.model.MoviesRepository
import com.example.actiatest.model.database.Movie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.popularMovies
}