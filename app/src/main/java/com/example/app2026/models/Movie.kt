package com.example.app2026.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_movies")
data class Movie(
    @PrimaryKey val id: Long = 0L,
    val title: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val overview: String = "",
    val genres: List<String> = emptyList(),
    val homepage: String = "",
    val imdbId: String = "",
    val viewType: String = MovieListType.POPULAR.dbValue
)