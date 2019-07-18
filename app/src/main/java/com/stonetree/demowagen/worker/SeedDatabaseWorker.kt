package com.stonetree.demowagen.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.stonetree.demowagen.constants.Database.WAGEN_FILE
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDatabase
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {

        try {
            applicationContext.assets.open(WAGEN_FILE).use { stream ->
                JsonReader(stream.reader()).use { reader ->
                    val wagen = object : TypeToken<List<Wagen>>() {}.type
                    val wagens: List<Wagen> = Gson().fromJson(reader, wagen)
                    val database = WagenDatabase.getInstance(applicationContext)
                    database.wagenDao().insertAll(wagens)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}