package com.example.app2026.models

data class VideosResponse(
    val results: List<MovieVideo> = emptyList()
)

data class MovieVideo(
    val name: String = "",
    val key: String = "",
    val site: String = "",
    val type: String = ""
)