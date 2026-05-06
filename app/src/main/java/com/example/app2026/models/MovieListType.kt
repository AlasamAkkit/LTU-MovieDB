package com.example.app2026.models

enum class MovieListType(val dbValue: String, val displayName: String) {
    POPULAR("popular", "Popular"),
    TOP_RATED("top_rated", "Top Rated");

    companion object {
        fun fromDbValue(value: String): MovieListType {
            return entries.firstOrNull { it.dbValue == value } ?: POPULAR
        }
    }
}
