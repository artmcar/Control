package com.artmcar.control.db.currency_rate

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usd_rates")
data class UsdRate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "numCode") val numCode: String,
    @ColumnInfo(name = "charCode") val charCode: String,
    @ColumnInfo(name = "nominal") val nominal: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "vunitRate") val vunitRate: String
)
