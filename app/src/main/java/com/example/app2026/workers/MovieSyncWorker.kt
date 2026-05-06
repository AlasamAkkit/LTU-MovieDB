package com.example.app2026.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.app2026.database.AppDatabase
import com.example.app2026.models.MovieListType
import com.example.app2026.repository.MovieRepository

class MovieSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val rawViewType = inputData.getString(KEY_VIEW_TYPE)
            ?: MovieListType.POPULAR.dbValue

        val viewType = MovieListType.fromDbValue(rawViewType)

        return try {
            val database = AppDatabase.getInstance(applicationContext)
            val repository = MovieRepository(database.movieDao())
            repository.syncMoviesForViewType(viewType)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val KEY_VIEW_TYPE = "key_view_type"
        const val UNIQUE_WORK_NAME = "movie_sync_work"
    }
}