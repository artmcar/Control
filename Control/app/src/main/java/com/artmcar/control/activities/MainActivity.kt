package com.artmcar.control.activities

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.artmcar.control.R
import com.artmcar.control.databinding.ActivityMainBinding
import com.artmcar.control.db.currency_rate.CurrencyDatabase
import com.artmcar.control.db.currency_rate.EurViewModel
import com.artmcar.control.db.currency_rate.EurViewModelFactory
import com.artmcar.control.db.currency_rate.UsdViewModel
import com.artmcar.control.db.currency_rate.UsdViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var eurViewModel: EurViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.usdIb.setOnClickListener{
            navController.navigate(R.id.usdFragment)
        }
        binding.eurIb.setOnClickListener{
            navController.navigate(R.id.eurFragment)
        }
        binding.homeIb.setOnClickListener{
            navController.navigate(R.id.mainFragment)
        }
        binding.settingsIb.setOnClickListener{
            navController.navigate(R.id.settingsFragment)
        }

        onBackPressedDispatcher.addCallback(this){
            if (navController.currentDestination?.id != R.id.mainFragment) {
                navController.popBackStack(R.id.mainFragment, false)
            }else {
                finish()
            }
        }


        val dao = CurrencyDatabase.getDatabase(applicationContext).eurRateDao()
        val factory = EurViewModelFactory(dao)
        eurViewModel = ViewModelProvider(this, factory).get(EurViewModel::class.java)

        eurViewModel.compareRates { isIncreased, currentRate ->
            runOnUiThread {
                updateEurUI(isIncreased, currentRate)
            }
        }

        val usdDao = CurrencyDatabase.getDatabase(applicationContext).usdRateDao()
        val usdFactory = UsdViewModelFactory(usdDao)
        val usdViewModel = ViewModelProvider(this, usdFactory).get(UsdViewModel::class.java)
        usdViewModel.compareRates { isIncreased, currentRate ->
            runOnUiThread {
                updateUsdUI(isIncreased, currentRate)
            }
        }


    }

    private fun updateUsdUI(isIncreased: Boolean?, currentRate: Double?) {
        val red = ContextCompat.getColor(this, R.color.currency_red)
        val green = ContextCompat.getColor(this, R.color.currency_green)
        val error_string = getString(R.string.error)

        binding.usdTv.text = currentRate?.let { String.format("%.4f", it) } ?: error_string

        when (isIncreased) {
            true -> {
                binding.usdIb.setColorFilter(red)
                binding.usdTv.setTextColor(red)
                binding.usdIv.setImageResource(R.drawable.ic_up_arrow)
                binding.usdIv.setColorFilter(red)
            }
            false -> {
                binding.usdIb.setColorFilter(green)
                binding.usdTv.setTextColor(green)
                binding.usdIv.setImageResource(R.drawable.ic_down_arrow)
                binding.usdIv.setColorFilter(green)
            }
            null -> {
                binding.usdIb.clearColorFilter()
                binding.usdTv.setTextColor(red)
                binding.usdIv.setImageResource(R.drawable.ic_issues)
                binding.usdIv.setColorFilter(red)
            }
        }
    }

    private fun updateEurUI(isIncreased: Boolean?, currentRate: Double?) {
        val red = ContextCompat.getColor(this, R.color.currency_red)
        val green = ContextCompat.getColor(this, R.color.currency_green)
        val error_string = getString(R.string.error)

        binding.eurTv.text = currentRate?.let{String.format("%.4f", it)} ?: error_string

        when (isIncreased) {
            true -> {
                binding.eurIb.setColorFilter(red)
                binding.eurTv.setTextColor(red)
                binding.eurIv.setImageResource(R.drawable.ic_up_arrow)
                binding.eurIv.setColorFilter(red)
            }
            false -> {
                binding.eurIb.setColorFilter(green)
                binding.eurTv.setTextColor(green)
                binding.eurIv.setImageResource(R.drawable.ic_down_arrow)
                binding.eurIv.setColorFilter(green)
            }
            null -> {
                binding.eurIb.clearColorFilter()
                binding.eurTv.setTextColor(red)
                binding.eurIv.setImageResource(R.drawable.ic_issues)
                binding.eurIv.setColorFilter(red)
            }
        }
    }

}