package com.stonetree.demowagen.data.wkda

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stonetree.demowagen.constants.Database
import java.io.Serializable

@Entity(
    tableName = Database.WKDA_TABLE,
    indices = [Index(Database.WKDA_ID)]
)
data class WKDA (
    @ColumnInfo(name = Database.WKDA_NAME) @SerializedName("name") val name: String = ""
) : Serializable {
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = Database.WKDA_ID)
    var id: String = ""
}