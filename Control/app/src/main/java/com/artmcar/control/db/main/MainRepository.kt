package com.artmcar.control.db.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java.math.BigDecimal

class MainRepository(private val mainDao: MainDao) {
    val getAllExpenses : LiveData<List<MainFields>> = mainDao.getAllExpenses()

    val getByAmount: LiveData<List<MainFields>> = mainDao.getByAmount()

    val getByBackAmount: LiveData<List<MainFields>> = mainDao.getByBackAmount()

    val getByDate: LiveData<List<MainFields>> = mainDao.getByDate()

    val getByBackDate: LiveData<List<MainFields>> = mainDao.getByBackDate()

    suspend fun insertExpense(mainFields: MainFields){
        mainDao.insertExpense(mainFields)
    }
    suspend fun updateExpense(mainFields: MainFields){
        mainDao.updateExpense(mainFields)
    }
    suspend fun deleteExpense(mainFields: MainFields){
        mainDao.deleteExpense(mainFields)
    }
    suspend fun deleteAllExpenses(){
        mainDao.deleteAllExpenses()
    }

    fun getTotalExpensesByCurrency(currency: String): LiveData<BigDecimal> {
        val result = MediatorLiveData<BigDecimal>()
        val source = mainDao.getExpensesAmountsByCurrency(currency)
        result.addSource(source) { list ->
            val sum = list.fold(BigDecimal.ZERO) { acc, item -> acc + item }
            result.value = sum
        }
        return result
    }

    fun getTotalIncomesByCurrency(currency: String): LiveData<BigDecimal> {
        val result = MediatorLiveData<BigDecimal>()
        val source = mainDao.getIncomesAmountsByCurrency(currency)
        result.addSource(source) { list ->
            val sum = list.fold(BigDecimal.ZERO) { acc, item -> acc + item }
            result.value = sum
        }
        return result
    }

}