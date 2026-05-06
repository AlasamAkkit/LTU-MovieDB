package com.example.app2026.models

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("results") val results: List<MovieListItemDto> = emptyList()
)

data class MovieListItemDto(
    @SerializedName("id") val id: Long = 0L
)

data class MovieDetailsDto(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("title") val title: String = "",
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("overview") val overview: String = "",
    @SerializedName("homepage") val homepage: String? = null,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("genres") val genres: List<GenreDto> = emptyList()
) {
    fun toEntity(viewType: MovieListType): Movie {
        return Movie(
            id = id,
            title = title,
            posterPath = posterPath.orEmpty(),
            backdropPath = backdropPath.orEmpty(),
            releaseDate = releaseDate.orEmpty(),
            overview = overview,
            genres = genres.map { it.name },
            homepage = homepage.orEmpty(),
            imdbId = imdbId.orEmpty(),
            viewType = viewType.dbValue
        )
    }
}

data class GenreDto(
    @SerializedName("name") val name: String = ""
)

data class ReviewsResponse(
    @SerializedName("results") val results: List<MovieReview> = emptyList()
)

data class MovieReview(
    @SerializedName("author") val author: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("url") val url: String = ""
)

data class VideosResponse(
    @SerializedName("results") val results: List<MovieVideo> = emptyList()
)

data class MovieVideo(
    @SerializedName("name") val name: String = "",
    @SerializedName("key") val key: String = "",
    @SerializedName("site") val site: String = "",
    @SerializedName("type") val type: String = ""
)
