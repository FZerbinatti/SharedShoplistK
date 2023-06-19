package com.dreamsphere.sharedshoplistk.repository.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_shiplist_id")

data class IdItem(

    @ColumnInfo(name = "id_shoplist")
    var spesa_id: String,
){

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
