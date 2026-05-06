package com.example.app2026.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app2026.models.CacheMetadataEntity
import com.example.app2026.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM cached_movies WHERE viewType = :viewType ORDER BY title ASC")
    fun observeMoviesByViewType(viewType: String): Flow<List<Movie>>

    @Query("SELECT * FROM cached_movies WHERE id = :movieId LIMIT 1")
    fun observeMovieById(movieId: Long): Flow<Movie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM cached_movies")
    suspend fun clearAllMovies()

    @Query("DELETE FROM cached_movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMetadata(metadata: CacheMetadataEntity)

    @Query("SELECT * FROM cache_metadata WHERE id = 0 LIMIT 1")
    suspend fun getMetadata(): CacheMetadataEntity?

    @Query("SELECT selectedViewType FROM cache_metadata WHERE id = 0 LIMIT 1")
    fun observeSelectedViewType(): Flow<String?>

    @Query("SELECT COUNT(*) FROM cached_movies WHERE viewType = :viewType")
    suspend fun countMoviesForViewType(viewType: String): Int
}