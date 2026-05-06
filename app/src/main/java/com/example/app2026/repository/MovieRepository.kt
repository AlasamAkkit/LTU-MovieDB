package com.example.app2026.repository

import com.example.app2026.database.MovieDao
import com.example.app2026.models.CacheMetadataEntity
import com.example.app2026.models.Movie
import com.example.app2026.models.MovieListType
import com.example.app2026.models.MovieReview
import com.example.app2026.models.MovieVideo
import com.example.app2026.network.TmdbClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val movieDao: MovieDao
) {

    fun observeMovies(viewType: MovieListType): Flow<List<Movie>> {
        return movieDao.observeMoviesByViewType(viewType.dbValue)
    }

    fun observeMovie(movieId: Long): Flow<Movie?> {
        return movieDao.observeMovieById(movieId)
    }

    fun observeSelectedViewType(): Flow<MovieListType> {
        return movieDao.observeSelectedViewType()
            .map { raw -> MovieListType.fromDbValue(raw ?: MovieListType.POPULAR.dbValue) }
            .distinctUntilChanged()
    }

    suspend fun ensureMetadataExists() {
        if (movieDao.getMetadata() == null) {
            movieDao.upsertMetadata(
                CacheMetadataEntity(selectedViewType = MovieListType.POPULAR.dbValue)
            )
        }
    }

    suspend fun setSelectedViewType(viewType: MovieListType) {
        val current = movieDao.getMetadata() ?: CacheMetadataEntity()
        movieDao.upsertMetadata(
            current.copy(selectedViewType = viewType.dbValue)
        )
    }

    suspend fun clearCache() {
        movieDao.clearAllMovies()
    }

    suspend fun syncMoviesForViewType(viewType: MovieListType) {
        val listResponse = when (viewType) {
            MovieListType.POPULAR -> TmdbClient.api.getPopularMovies()
            MovieListType.TOP_RATED -> TmdbClient.api.getTopRatedMovies()
        }

        val detailedMovies = listResponse.results
            .take(20)
            .map { itemDto ->
                TmdbClient.api.getMovieDetails(itemDto.id).toEntity(viewType)
            }

        movieDao.clearAllMovies()
        movieDao.insertMovies(detailedMovies)

        val current = movieDao.getMetadata() ?: CacheMetadataEntity()
        movieDao.upsertMetadata(
            current.copy(
                selectedViewType = viewType.dbValue,
                lastSyncedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun getReviews(movieId: Long): List<MovieReview> {
        return TmdbClient.api.getMovieReviews(movieId).results
    }

    suspend fun getVideos(movieId: Long): List<MovieVideo> {
        return TmdbClient.api.getMovieVideos(movieId).results
    }

    suspend fun hasCachedMoviesFor(viewType: MovieListType): Boolean {
        return movieDao.countMoviesForViewType(viewType.dbValue) > 0
    }
}