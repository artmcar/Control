package com.artmcar.control.db

import androidx.lifecycle.LiveData

class MainRepository(private val mainDao: MainDao) {
    val getAllExpenses : LiveData<List<MainFields>> = mainDao.getAllExpenses()

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
}