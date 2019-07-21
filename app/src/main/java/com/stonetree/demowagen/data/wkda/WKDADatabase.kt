package com.stonetree.demowagen.data.wkda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WKDA::class], version = 1, exportSchema = false)
abstract class WKDADatabase : RoomDatabase() {

    abstract fun wkdaDao(): WKDADao

    companion object {

        @Volatile private var instance: WKDADatabase? = null
        fun getInstance(context: Context): WKDADatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WKDADatabase {
            return Room.databaseBuilder(context, WKDADatabase::class.java,
                com.stonetree.demowagen.constants.Database.WKDA_DATABASE_NAME
            ).build()
        }
    }
}