package com.artmcar.control.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MainDao {
    @Insert
    suspend fun insertExpense(mainFields: MainFields)

    @Delete
    suspend fun deleteExpense(mainFields: MainFields)

    @Update
    suspend fun updateExpense(mainFields: MainFields)

    @Query("DELETE FROM expenses")
    suspend fun deleteAllExpenses()

    @Query("SELECT * FROM expenses ORDER BY id ASC")
    fun getAllExpenses(): LiveData<List<MainFields>>
}