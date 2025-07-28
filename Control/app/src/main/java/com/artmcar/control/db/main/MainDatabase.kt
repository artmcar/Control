//MainDatabase

package com.artmcar.control.db.main

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MainFields::class],
    version = 6,
    autoMigrations = [
        AutoMigration (from = 5, to = 6)
    ]
)
@TypeConverters(Converters::class)
abstract class MainDatabase: RoomDatabase() {
    abstract fun mainDao(): MainDao

    companion object{
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "control_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}