package com.artmcar.control.db.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal


@Entity(tableName = "expenses")
data class MainFields(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "amount") val amount: BigDecimal,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "month") val month: Int,
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "operation") val operation: String
): Serializable
