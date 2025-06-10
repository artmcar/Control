package com.artmcar.control.currencies

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("XML_daily.asp")
    suspend fun getCurrencyRates(@Query("date_req") date: String): ValCurs

    companion object {
        private const val BASE_URL = "https://www.cbr.ru/scripts/"

        fun create(): CurrencyService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(CurrencyService::class.java)
        }
    }
}