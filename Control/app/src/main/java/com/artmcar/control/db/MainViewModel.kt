package com.artmcar.control.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    val getAllExpenses: LiveData<List<MainFields>>
    private val repository: MainRepository

    init {
        val mainDao = MainDatabase.getDatabase(application).mainDao()
        repository = MainRepository(mainDao)
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