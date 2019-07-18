package com.stonetree.demowagen.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.stonetree.demowagen.constants.Database.BUILT_DATE_COLUMN
import com.stonetree.demowagen.constants.Database.CAR_TYPE_COLUMN
import com.stonetree.demowagen.constants.Database.MANUFACTURER_ID_COLUMN
import com.stonetree.demowagen.constants.Database.MANUFACTURER_NAME_COLUMN
import com.stonetree.demowagen.constants.Database.WAGEN_PK
import com.stonetree.demowagen.constants.Database.WAGEN_TABLE

@Entity(
    tableName = WAGEN_TABLE,
    indices = [Index(WAGEN_PK)]
)
data class Wagen (
    @ColumnInfo(name = MANUFACTURER_ID_COLUMN) val manufacturerId: Int,
    @ColumnInfo(name = MANUFACTURER_NAME_COLUMN) val name: String,
    @ColumnInfo(name = CAR_TYPE_COLUMN) val carType: String,
    @ColumnInfo(name = BUILT_DATE_COLUMN) val builtDate: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WAGEN_PK)
    var id: Long = 0
}