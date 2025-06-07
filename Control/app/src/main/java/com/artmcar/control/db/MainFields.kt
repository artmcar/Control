package com.artmcar.control.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class MainFields(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "amount") val amount: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "description") val description: String
)
