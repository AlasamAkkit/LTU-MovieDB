package com.example.app2026.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_metadata")
data class CacheMetadataEntity(
    @PrimaryKey val id: Int = 0,
    val selectedViewType: String = MovieListType.POPULAR.dbValue,
    val lastSyncedAt: Long = 0L
)
