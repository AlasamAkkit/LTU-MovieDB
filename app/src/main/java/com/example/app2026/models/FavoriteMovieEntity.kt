package com.example.app2026.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val posterPath: String,
    val releaseDate: String
)