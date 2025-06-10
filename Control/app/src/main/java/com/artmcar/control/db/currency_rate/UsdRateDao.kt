package com.artmcar.control.db.currency_rate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsdRateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rate: UsdRate)

    @Query("SELECT * FROM usd_rates WHERE date = :date LIMIT 1")
    suspend fun getRateByDate(date: String): UsdRate?

    @Query("SELECT * FROM usd_rates ORDER BY date DESC")
    suspend fun getAllRatesDesc(): List<UsdRate>
}