package com.example.app2026.repository

import com.example.app2026.models.MovieReview
import com.example.app2026.models.MovieVideo
import com.example.app2026.network.TmdbClient

class TmdbRepository {
    suspend fun getReviews(movieId: Long): List<MovieReview> {
        return TmdbClient.api.getMovieReviews(movieId).results
    }

    suspend fun getVideos(movieId: Long): List<MovieVideo> {
        return TmdbClient.api.getMovieVideos(movieId).results
    }
}