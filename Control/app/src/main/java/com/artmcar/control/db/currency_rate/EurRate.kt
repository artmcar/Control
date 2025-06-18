package com.artmcar.control.db.currency_rate

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eur_rates")
data class EurRate(
    @PrimaryKey
    val date: String,
    @ColumnInfo(name = "numCode") val numCode: String,
    @ColumnInfo(name = "charCode") val charCode: String,
    @ColumnInfo(name = "nominal") val nominal: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "vunitRate") val vunitRate: String
)
