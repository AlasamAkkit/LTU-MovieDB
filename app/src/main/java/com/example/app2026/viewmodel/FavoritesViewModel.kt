package com.example.app2026.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app2026.database.AppDatabase
import com.example.app2026.models.FavoriteMovieEntity
import com.example.app2026.models.Movie
import com.example.app2026.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoritesRepository

    val favoritesFlow: Flow<List<FavoriteMovieEntity>>

    init {
        val dao = AppDatabase.getInstance(application).favoriteMovieDao()
        repository = FavoritesRepository(dao)
        favoritesFlow = repository.favoritesFlow
    }

    fun isFavorite(movieId: Long): Flow<Boolean> {
        return repository.isFavoriteFlow(movieId)
    }

    fun addFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.addFavorite(movie)
        }
    }

    fun removeFavoriteById(movieId: Long) {
        viewModelScope.launch {
            repository.removeFavoriteById(movieId)
        }
    }

    fun removeFavorite(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
        }
    }
}