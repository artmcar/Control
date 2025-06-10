package com.artmcar.control.db.currency_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EurViewModel(private val eurRateDao: EurRateDao) : ViewModel() {

    private val _eurRates = MutableLiveData<List<EurRate>>()
    val eurRates: LiveData<List<EurRate>> get() = _eurRates

    fun loadRates() {
        viewModelScope.launch {
            val rates = eurRateDao.getAllRatesDesc()
            _eurRates.postValue(rates)
        }
    }

    fun compareRates(onResult: (isIncreased: Boolean?, currentRate: Double?) -> Unit) {
        viewModelScope.launch {
            val today = LocalDate.now()
            val yesterday = today.minusDays(1)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val todayStr = today.format(formatter)
            val yesterdayStr = yesterday.format(formatter)

            val todayRate = eurRateDao.getRateByDate(todayStr)
            val yesterdayRate = eurRateDao.getRateByDate(yesterdayStr)

            if (todayRate != null && yesterdayRate != null) {
                val todayValue = todayRate.value.replace(',', '.').toDoubleOrNull()
                val yesterdayValue = yesterdayRate.value.replace(',', '.').toDoubleOrNull()
                if (todayValue != null && yesterdayValue != null) {
                    onResult(todayValue > yesterdayValue, todayValue)
                    return@launch
                }
            }

            onResult(null, null)
        }
    }
}

class EurViewModelFactory(private val eurRateDao: EurRateDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EurViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EurViewModel(eurRateDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}