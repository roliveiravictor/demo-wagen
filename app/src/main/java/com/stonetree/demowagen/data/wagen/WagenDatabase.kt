package com.stonetree.demowagen.data.wagen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stonetree.demowagen.constants.Database.WAGEN_DATABASE_NAME

@Database(entities = [Wagen::class], version = 1, exportSchema = false)
abstract class WagenDatabase : RoomDatabase() {

    abstract fun wagenDao(): WagenDao

    companion object {

        @Volatile private var instance: WagenDatabase? = null
        fun getInstance(context: Context): WagenDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WagenDatabase {
            return Room.databaseBuilder(context, WagenDatabase::class.java, WAGEN_DATABASE_NAME)
                .build()
        }
    }
}