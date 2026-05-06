package com.example.app2026.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.app2026.models.CacheMetadataEntity
import com.example.app2026.models.Movie

@Database(
    entities = [Movie::class, CacheMetadataEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movie_cache.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}