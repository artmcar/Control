package com.artmcar.control.db.currency_rate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    fun loadRatesForDates(dates: List<String>) {
        viewModelScope.launch {
            dates.forEach { date ->
                val error = repository.fetchAndStoreRates(date)
                if (error != null) {
                    _errorFlow.emit("Error loading rates on $date: $error")
                }
            }
        }
    }
}