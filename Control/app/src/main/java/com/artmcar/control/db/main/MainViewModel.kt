package com.artmcar.control.db.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    val getAllExpenses: LiveData<List<MainFields>>
    val getByAmount: LiveData<List<MainFields>>
    val getByBackAmount: LiveData<List<MainFields>>
    val getByDate: LiveData<List<MainFields>>
    val getByBackDate: LiveData<List<MainFields>>
    private val repository: MainRepository

    init {
        val mainDao = MainDatabase.getDatabase(application).mainDao()
        repository = MainRepository(mainDao)
        getByAmount = repository.getByAmount
        getByBackAmount = repository.getByBackAmount
        getByDate = repository.getByDate
        getByBackDate = repository.getByBackDate
        getAllExpenses = repository.getAllExpenses
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