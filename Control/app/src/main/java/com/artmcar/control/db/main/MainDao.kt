package com.artmcar.control.db.main

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

    @Query("SELECT * FROM expenses ORDER BY year DESC, month DESC, day DESC")
    fun getAllExpenses(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY amount * 1 DESC")
    fun getByAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY amount * 1 ASC")
    fun getByBackAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY year ASC, month ASC, day ASC")
    fun getByDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY year DESC, month DESC, day DESC")
    fun getByBackDate(): LiveData<List<MainFields>>
}