package com.example.app2026.network

import com.example.app2026.models.MovieDetailsDto
import com.example.app2026.models.MovieListResponse
import com.example.app2026.models.ReviewsResponse
import com.example.app2026.models.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long
    ): MovieDetailsDto

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Long
    ): ReviewsResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Long
    ): VideosResponse
}