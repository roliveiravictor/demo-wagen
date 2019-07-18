package com.stonetree.demowagen.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.stonetree.demowagen.constants.Database.DATABASE_NAME
import com.stonetree.demowagen.worker.SeedDatabaseWorker

@Database(entities = [Wagen::class], version = 1, exportSchema = false)
abstract class WagenDatabase : RoomDatabase() {

    abstract fun wagenDao(): WagenDao

    companion object {

        @Volatile private var instance: WagenDatabase? = null

        fun getInstance(context: Context): WagenDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WagenDatabase {
            return Room.databaseBuilder(context, WagenDatabase::class.java, DATABASE_NAME)
                .addCallback(databaseCallback(context))
                .build()
        }

        private fun databaseCallback(context: Context): Callback {
            return object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build().apply {
                        WorkManager.getInstance(context).enqueue(this)
                    }
                }
            }
        }

    }
}