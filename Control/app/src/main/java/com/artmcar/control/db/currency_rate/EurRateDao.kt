package com.artmcar.control.db.currency_rate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EurRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rate: EurRate)

    @Query("SELECT * FROM eur_rates WHERE date = :date LIMIT 1")
    suspend fun getRateByDate(date: String): EurRate?

    @Query("SELECT * FROM eur_rates ORDER BY date DESC")
    suspend fun getAllRatesDesc(): List<EurRate>
}