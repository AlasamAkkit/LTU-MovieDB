package com.example.app2026.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenres(genres: List<String>): String {
        return genres.joinToString(separator = "|||")
    }

    @TypeConverter
    fun toGenres(raw: String): List<String> {
        if (raw.isBlank()) return emptyList()
        return raw.split("|||")
    }
}