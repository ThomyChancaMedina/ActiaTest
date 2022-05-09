package com.example.actiatest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.actiatest.domain.GetPopularMoviesUseCase
import com.example.actiatest.domain.RequestPopularMoviesUseCase
import com.example.actiatest.model.Error
import com.example.actiatest.model.database.Movie
import com.example.actiatest.model.toError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val getPopularMoviesUsaCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUsaCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.update { UiState(movies = movies) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.update { _state.value.copy(loading = false, error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val getPopularMoviesUsaCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase

) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(requestPopularMoviesUseCase, getPopularMoviesUsaCase) as T
    }
}