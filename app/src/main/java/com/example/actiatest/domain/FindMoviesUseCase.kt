package com.example.actiatest.domain

import com.example.actiatest.model.MoviesRepository

class FindMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(id: Int)=repository.findById(id)
}