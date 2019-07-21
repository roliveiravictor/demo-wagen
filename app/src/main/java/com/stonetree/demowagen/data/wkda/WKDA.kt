package com.stonetree.demowagen.data.wkda

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stonetree.demowagen.constants.Database
import java.io.Serializable

@Entity(
    tableName = Database.WKDA_TABLE,
    indices = [Index(Database.WKDA_PK)]
)
data class WKDA (
    @ColumnInfo(name = Database.WKDA_ID) @SerializedName("id") val id: String = "",
    @ColumnInfo(name = Database.WKDA_NAME) @SerializedName("name") val name: String = ""
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Database.WKDA_PK)
    var id_pk: Long = 0
}