package com.stonetree.demowagen.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WagenDao {

    @Query("SELECT * FROM wagen")
    fun getWagen(): LiveData<Wagen>

    @Query("UPDATE wagen SET manufacturer_id=:manufacturerId")
    fun updateManufacturerId(manufacturerId: Int)

    @Query("UPDATE wagen SET manufacturer_name=:name")
    fun updateManufacturerName(name: String)

    @Query("UPDATE wagen SET car_type=:carType")
    fun updateCarType(carType: String)

    @Query("UPDATE wagen SET built_date=:builtDate")
    fun updateBuiltDate(builtDate: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(wagen: List<Wagen>)
}