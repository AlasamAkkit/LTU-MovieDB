package com.example.app2026.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app2026.database.AppDatabase
import com.example.app2026.models.Movie
import com.example.app2026.models.MovieListType
import com.example.app2026.models.MovieReview
import com.example.app2026.models.MovieVideo
import com.example.app2026.repository.MovieRepository
import com.example.app2026.utils.ConnectivityObserver
import com.example.app2026.utils.NetworkConnectivityObserver
import com.example.app2026.workers.MovieSyncScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(
        AppDatabase.getInstance(application).movieDao()
    )

    private val connectivityObserver = NetworkConnectivityObserver(application)

    private val _selectedViewType = MutableStateFlow(MovieListType.POPULAR)
    val selectedViewType: StateFlow<MovieListType> = _selectedViewType

    private val _connectivityStatus =
        MutableStateFlow(ConnectivityObserver.Status.Unavailable)

    val isConnected: StateFlow<Boolean> = _connectivityStatus
        .map { status -> status == ConnectivityObserver.Status.Available }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )

    val movies: StateFlow<List<Movie>> = _selectedViewType
        .flatMapLatest { type -> repository.observeMovies(type) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            repository.ensureMetadataExists()
        }

        viewModelScope.launch {
            repository.observeSelectedViewType().collect { type ->
                if (_selectedViewType.value != type) {
                    _selectedViewType.value = type
                }
                enqueueSync(type)
            }
        }

        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                val oldConnected = _connectivityStatus.value == ConnectivityObserver.Status.Available
                val newConnected = status == ConnectivityObserver.Status.Available
                _connectivityStatus.value = status

                if (!oldConnected && newConnected) {
                    enqueueSync(_selectedViewType.value)
                }
            }
        }
    }

    fun selectViewType(type: MovieListType) {
        viewModelScope.launch {
            repository.setSelectedViewType(type)
        }
    }

    fun retryCurrentSelection() {
        enqueueSync(_selectedViewType.value)
    }

    fun clearCachedList() {
        viewModelScope.launch {
            repository.clearCache()
        }
    }

    fun observeMovie(movieId: Long) = repository.observeMovie(movieId)

    suspend fun getReviews(movieId: Long): List<MovieReview> {
        return repository.getReviews(movieId)
    }

    suspend fun getVideos(movieId: Long): List<MovieVideo> {
        return repository.getVideos(movieId)
    }

    private fun enqueueSync(viewType: MovieListType) {
        MovieSyncScheduler.enqueueSync(
            context = getApplication(),
            viewType = viewType
        )
    }
}