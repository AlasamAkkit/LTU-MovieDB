package com.example.app2026.repository

import com.example.app2026.database.FavoriteMovieDao
import com.example.app2026.models.FavoriteMovieEntity
import com.example.app2026.models.Movie
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val favoriteMovieDao: FavoriteMovieDao
) {
    val favoritesFlow: Flow<List<FavoriteMovieEntity>> =
        favoriteMovieDao.observeAllFavorites()

    fun isFavoriteFlow(movieId: Long): Flow<Boolean> {
        return favoriteMovieDao.observeIsFavorite(movieId)
    }

    suspend fun addFavorite(movie: Movie) {
        favoriteMovieDao.insertFavorite(
            FavoriteMovieEntity(
                id = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate
            )
        )
    }

    suspend fun removeFavoriteById(movieId: Long) {
        favoriteMovieDao.deleteById(movieId)
    }

    suspend fun removeFavorite(movie: FavoriteMovieEntity) {
        favoriteMovieDao.deleteFavorite(movie)
    }
}