package com.artmcar.control.db.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal


class MainViewModel(application: Application): AndroidViewModel(application) {

    val rubExpenses: LiveData<BigDecimal>
    val usdExpenses: LiveData<BigDecimal>
    val eurExpenses: LiveData<BigDecimal>
    val amdExpenses: LiveData<BigDecimal>

    val rubIncomes: LiveData<BigDecimal>
    val usdIncomes: LiveData<BigDecimal>
    val eurIncomes: LiveData<BigDecimal>
    val amdIncomes: LiveData<BigDecimal>

    val getAllExpenses: LiveData<List<MainFields>>
    val getByAmount: LiveData<List<MainFields>>
    val getByBackAmount: LiveData<List<MainFields>>
    val getByDate: LiveData<List<MainFields>>
    val getByBackDate: LiveData<List<MainFields>>

    val getByRubByDate: LiveData<List<MainFields>>
    val getByRubByBackDate: LiveData<List<MainFields>>
    val getByRubByAmount: LiveData<List<MainFields>>
    val getByRubByBackAmount: LiveData<List<MainFields>>

    val getByUsdByDate: LiveData<List<MainFields>>
    val getByUsdByBackDate: LiveData<List<MainFields>>
    val getByUsdByAmount: LiveData<List<MainFields>>
    val getByUsdByBackAmount: LiveData<List<MainFields>>

    val getByEurByDate: LiveData<List<MainFields>>
    val getByEurByBackDate: LiveData<List<MainFields>>
    val getByEurByAmount: LiveData<List<MainFields>>
    val getByEurByBackAmount: LiveData<List<MainFields>>

    val getByAmdByDate: LiveData<List<MainFields>>
    val getByAmdByBackDate: LiveData<List<MainFields>>
    val getByAmdByAmount: LiveData<List<MainFields>>
    val getByAmdByBackAmount: LiveData<List<MainFields>>

    private val repository: MainRepository

    init {
        val mainDao = MainDatabase.getDatabase(application).mainDao()
        repository = MainRepository(mainDao)
        getByAmount = repository.getByAmount
        getByBackAmount = repository.getByBackAmount
        getByDate = repository.getByDate
        getByBackDate = repository.getByBackDate
        getAllExpenses = repository.getAllExpenses

        getByRubByDate = repository.getByRubByDate
        getByRubByBackDate = repository.getByRubByBackDate
        getByRubByAmount = repository.getByRubByAmount
        getByRubByBackAmount = repository.getByRubByBackAmount

        getByUsdByDate = repository.getByUsdByDate
        getByUsdByBackDate = repository.getByUsdByBackDate
        getByUsdByAmount = repository.getByUsdByAmount
        getByUsdByBackAmount = repository.getByUsdByBackAmount

        getByEurByDate = repository.getByEurByDate
        getByEurByBackDate = repository.getByEurByBackDate
        getByEurByAmount = repository.getByEurByAmount
        getByEurByBackAmount = repository.getByEurByBackAmount

        getByAmdByDate = repository.getByAmdByDate
        getByAmdByBackDate = repository.getByAmdByBackDate
        getByAmdByAmount = repository.getByAmdByAmount
        getByAmdByBackAmount = repository.getByAmdByBackAmount

        rubExpenses = repository.getTotalExpensesByCurrency("RUB")
        usdExpenses = repository.getTotalExpensesByCurrency("USD")
        eurExpenses = repository.getTotalExpensesByCurrency("EUR")
        amdExpenses = repository.getTotalExpensesByCurrency("AMD")

        rubIncomes = repository.getTotalIncomesByCurrency("RUB")
        usdIncomes = repository.getTotalIncomesByCurrency("USD")
        eurIncomes = repository.getTotalIncomesByCurrency("EUR")
        amdIncomes = repository.getTotalIncomesByCurrency("AMD")
    }

    fun insertExpense(mainFields: MainFields){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertExpense(mainFields)
        }
    }

    fun updateExpense(mainFields: MainFields){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateExpense(mainFields)
        }
    }

    fun deleteExpense(mainFields: MainFields){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteExpense(mainFields)
        }
    }

    fun deleteAllExpenses(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllExpenses()
        }
    }

}