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

    val getByRubByDate: LiveData<List<MainFields>> = mainDao.getByRubByDate()
    val getByRubByBackDate: LiveData<List<MainFields>> = mainDao.getByRubByBackDate()
    val getByRubByAmount: LiveData<List<MainFields>> = mainDao.getByRubByAmount()
    val getByRubByBackAmount: LiveData<List<MainFields>> = mainDao.getByRubByBackAmount()

    val getByUsdByDate: LiveData<List<MainFields>> = mainDao.getByUsdByDate()
    val getByUsdByBackDate: LiveData<List<MainFields>> = mainDao.getByUsdByBackDate()
    val getByUsdByAmount: LiveData<List<MainFields>> = mainDao.getByUsdByAmount()
    val getByUsdByBackAmount: LiveData<List<MainFields>> = mainDao.getByUsdByBackAmount()

    val getByEurByDate: LiveData<List<MainFields>> = mainDao.getByEurByDate()
    val getByEurByBackDate: LiveData<List<MainFields>> = mainDao.getByEurByBackDate()
    val getByEurByAmount: LiveData<List<MainFields>> = mainDao.getByEurByAmount()
    val getByEurByBackAmount: LiveData<List<MainFields>> = mainDao.getByEurByBackAmount()

    val getByAmdByDate: LiveData<List<MainFields>> = mainDao.getByAmdByDate()
    val getByAmdByBackDate: LiveData<List<MainFields>> = mainDao.getByAmdByBackDate()
    val getByAmdByAmount: LiveData<List<MainFields>> = mainDao.getByAmdByAmount()
    val getByAmdByBackAmount: LiveData<List<MainFields>> = mainDao.getByAmdByBackAmount()


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