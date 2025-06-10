package com.artmcar.control.db.currency_rate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class UsdViewModel(private val usdRateDao: UsdRateDao) : ViewModel() {

    private val _usdRates = MutableLiveData<List<UsdRate>>()
    val usdRates: LiveData<List<UsdRate>> get() = _usdRates

    fun loadRates() {
        viewModelScope.launch {
            val rates = usdRateDao.getAllRatesDesc()
            _usdRates.postValue(rates)
        }
    }
    fun compareRates(onResult: (isIncreased: Boolean?, currentRate: Double?) -> Unit) {
        viewModelScope.launch {
            val today = LocalDate.now()
            val yesterday = today.minusDays(1)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val todayStr = today.format(formatter)
            val yesterdayStr = yesterday.format(formatter)

            val todayRate = usdRateDao.getRateByDate(todayStr)
            val yesterdayRate = usdRateDao.getRateByDate(yesterdayStr)

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

class UsdViewModelFactory(private val usdRateDao: UsdRateDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsdViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsdViewModel(usdRateDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}