package com.artmcar.control.db.currency_rate

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsdRate::class, EurRate::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun usdRateDao(): UsdRateDao
    abstract fun eurRateDao(): EurRateDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getDatabase(context: Context): CurrencyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "currency_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}