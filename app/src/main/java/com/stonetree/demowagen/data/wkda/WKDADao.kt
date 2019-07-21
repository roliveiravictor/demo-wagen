package com.stonetree.demowagen.data.wkda

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface WKDADao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(wagens: List<WKDA>)

    @Query("SELECT * FROM wkda")
    fun get(): List<WKDA>

    @Query("SELECT * FROM wkda")
    fun getAll(): DataSource.Factory<Int, WKDA>

    @Query("DELETE FROM wkda")
    fun clear()
}