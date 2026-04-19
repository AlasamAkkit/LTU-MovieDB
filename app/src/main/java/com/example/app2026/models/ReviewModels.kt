
package com.example.app2026.models

data class ReviewsResponse(
    val results: List<MovieReview> = emptyList()
)

data class MovieReview(
    val author: String = "",
    val content: String = "",
    val url: String = ""
)