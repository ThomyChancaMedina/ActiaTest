package com.example.actiatest.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.actiatest.domain.FindMoviesUseCase
import com.example.actiatest.model.Error
import com.example.actiatest.model.database.Movie
import com.example.actiatest.model.toError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
     movieId: Int,
     findMoviesUseCase: FindMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {

            findMoviesUseCase(movieId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movie -> _state.update { UiState(movie = movie) } }
        }
    }


    data class UiState(val movie: Movie? = null, val error: Error? = null)
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val movieId: Int,
    private val findMoviesUseCase: FindMoviesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movieId, findMoviesUseCase) as T
    }
}