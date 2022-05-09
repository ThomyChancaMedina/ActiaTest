package com.example.actiatest.domain

import com.example.actiatest.model.Error
import com.example.actiatest.model.MoviesRepository

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error?{
        return moviesRepository.requestPopularMovies()
    }
}