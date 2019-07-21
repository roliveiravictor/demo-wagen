package com.stonetree.demowagen.data.wkda

import androidx.room.*

@Dao
interface WKDADao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(wagens: List<WKDA>)

    @Query("SELECT * FROM wkda")
    fun getAll(): List<WKDA>

    @Query("DELETE FROM wkda")
    fun clear()
}