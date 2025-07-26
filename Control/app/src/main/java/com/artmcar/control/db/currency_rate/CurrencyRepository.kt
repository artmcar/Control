package com.artmcar.control.db.currency_rate

import com.artmcar.control.currencies.CurrencyService

class CurrencyRepository(
    private val service: CurrencyService,
    private val db: CurrencyDatabase
) {
    suspend fun fetchAndStoreRates(date: String): String? {
        return try {

            val usdInDb = db.usdRateDao().getRateByDate(date)
            val eurInDb = db.eurRateDao().getRateByDate(date)
            if (usdInDb != null && eurInDb != null) {
                return null
            }

            val response = service.getCurrencyRates(date)
            val usd = response.valuteList?.find { it.charCode == "USD" }
            val eur = response.valuteList?.find { it.charCode == "EUR" }

            val (day, month, year) = date.split("/").map { it.toInt() }

            usd?.let {
                db.usdRateDao().insert(
                    UsdRate(
                        date = date,
                        year = year,
                        month = month,
                        day = day,
                        numCode = it.numCode.toString(),
                        charCode = it.charCode,
                        nominal = it.nominal,
                        name = it.name,
                        value = it.value,
                        vunitRate = it.vunitRate
                    )
                )
            }

            eur?.let {
                db.eurRateDao().insert(
                    EurRate(
                        date = date,
                        year = year,
                        month = month,
                        day = day,
                        numCode = it.numCode.toString(),
                        charCode = it.charCode,
                        nominal = it.nominal,
                        name = it.name,
                        value = it.value,
                        vunitRate = it.vunitRate
                    )
                )
            }

            null
        } catch (e: Exception) {
            e.message
        }
    }
}