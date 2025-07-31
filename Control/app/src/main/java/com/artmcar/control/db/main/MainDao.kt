package com.artmcar.control.db.main

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.math.BigDecimal

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

    @Query("SELECT * FROM expenses ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC, second DESC")
    fun getAllExpenses(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY amount * 1 DESC")
    fun getByAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY amount * 1 ASC")
    fun getByBackAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY year ASC, month ASC, day ASC, hour ASC, minute ASC, second ASC")
    fun getByDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC, second DESC")
    fun getByBackDate(): LiveData<List<MainFields>>

    @Query("SELECT amount FROM expenses WHERE operation = 'Expense' AND currency = :currency")
    fun getExpensesAmountsByCurrency(currency: String): LiveData<List<BigDecimal>>

    @Query("SELECT amount FROM expenses WHERE operation = 'Income' AND currency = :currency")
    fun getIncomesAmountsByCurrency(currency: String): LiveData<List<BigDecimal>>

    @Query("SELECT * FROM expenses WHERE currency = 'RUB' ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC, second DESC")
    fun getByRubByDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'RUB' ORDER BY year ASC, month ASC, day ASC, hour ASC, minute ASC, second ASC")
    fun getByRubByBackDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'RUB' ORDER BY amount * 1 DESC")
    fun getByRubByAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'RUB' ORDER BY amount * 1 ASC")
    fun getByRubByBackAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'USD' ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC, second DESC")
    fun getByUsdByDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'USD' ORDER BY year ASC, month ASC, day ASC, hour ASC, minute ASC, second ASC")
    fun getByUsdByBackDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'USD' ORDER BY amount * 1 DESC")
    fun getByUsdByAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'USD' ORDER BY amount * 1 ASC")
    fun getByUsdByBackAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'EUR' ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC, second DESC")
    fun getByEurByDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'EUR' ORDER BY year ASC, month ASC, day ASC, hour ASC, minute ASC, second ASC")
    fun getByEurByBackDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'EUR' ORDER BY amount * 1 DESC")
    fun getByEurByAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'EUR' ORDER BY amount * 1 ASC")
    fun getByEurByBackAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'AMD' ORDER BY year DESC, month DESC, day DESC, hour DESC, minute DESC, second DESC")
    fun getByAmdByDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'AMD' ORDER BY year ASC, month ASC, day ASC, hour ASC, minute ASC, second ASC")
    fun getByAmdByBackDate(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'AMD' ORDER BY amount * 1 DESC")
    fun getByAmdByAmount(): LiveData<List<MainFields>>

    @Query("SELECT * FROM expenses WHERE currency = 'AMD' ORDER BY amount * 1 ASC")
    fun getByAmdByBackAmount(): LiveData<List<MainFields>>
}