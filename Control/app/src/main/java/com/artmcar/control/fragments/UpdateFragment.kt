//UpdateFragment

package com.artmcar.control.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.artmcar.control.R
import com.artmcar.control.databinding.FragmentAddBinding
import com.artmcar.control.db.main.MainFields
import com.artmcar.control.db.main.MainViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Calendar


class UpdateFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private var selected_currency = "RUB"
    private var selected_type = "Other"
    private var currentItemId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Getting the data
        val item = requireActivity().intent.getSerializableExtra("item_data") as? MainFields
        item?.let {
            binding.amountEt.setText(it.amount.toPlainString())
            binding.yearEt.setText(it.year.toString())
            binding.monthEt.setText(it.month.toString())
            binding.dayEt.setText(it.day.toString())
            binding.hourEt.setText(it.hour.toString())
            binding.minuteEt.setText(it.minute.toString())
            binding.secondEt.setText(it.second.toString())
            binding.descriptionEt.setText(it.description)
            binding.expIncSwitch.isChecked = it.operation == "Income"

            val currencyIndex = when (it.currency) {
                "RUB" -> 0
                "USD" -> 1
                "EUR" -> 2
                "AMD" -> 3
                else -> 0
            }
            binding.currencySpinner.setSelection(currencyIndex)

            val typeArray = resources.getStringArray(R.array.expenses_type)
            val typeIndex = typeArray.indexOf(it.type)
            if (typeIndex >= 0) binding.typeSpinner.setSelection(typeIndex)

            selected_currency = it.currency
            selected_type = it.type
            currentItemId = it.id
        }



        binding.setDataButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendar_year = calendar.get(Calendar.YEAR)
            val calendar_month = calendar.get(Calendar.MONTH) + 1
            val calendar_day = calendar.get(Calendar.DAY_OF_MONTH)
            binding.yearEt.setText(calendar_year.toString())
            binding.monthEt.setText(String.format("%02d", calendar_month))
            binding.dayEt.setText(String.format("%02d", calendar_day))
        }
        binding.setTimeButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendar_hour = calendar.get(Calendar.HOUR_OF_DAY)
            val calendar_minute = calendar.get(Calendar.MINUTE)
            val calendar_second = calendar.get(Calendar.SECOND)
            binding.hourEt.setText(String.format("%02d", calendar_hour))
            binding.minuteEt.setText(String.format("%02d", calendar_minute))
            binding.secondEt.setText(String.format("%02d", calendar_second))
        }

        val type_array = resources.getStringArray(R.array.expenses_type)
        selected_type = type_array[0]
        binding.currencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                selected_currency = when(pos){
                    0 -> "RUB"
                    1 -> "USD"
                    2 -> "EUR"
                    3 -> "AMD"
                    else -> "RUB"
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.typeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                selected_type = type_array[pos]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addToolbar.inflateMenu(R.menu.add_menu)
        binding.addToolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.addToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.addToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_save_item -> {
                    updateItem()
                    true
                }
                else -> false
            }
        }
    }

    private fun updateItem(){
        val amount = binding.amountEt.text.toString().toBigDecimalOrNull()?.setScale(2, RoundingMode.HALF_EVEN)
        val year = binding.yearEt.text.toString().toIntOrNull()
        val month = binding.monthEt.text.toString().toIntOrNull()
        val day = binding.dayEt.text.toString().toIntOrNull()
        val hour = binding.hourEt.text.toString().toIntOrNull()
        val minute = binding.minuteEt.text.toString().toIntOrNull()
        val second = binding.secondEt.text.toString().toIntOrNull()
        val description = binding.descriptionEt.text.toString().ifBlank { getString(R.string.no_description) }
        val operation = if(binding.expIncSwitch.isChecked) "Income" else "Expense"

        val today_calendar = Calendar.getInstance()
        val entered_calendar = Calendar.getInstance().apply {
            if (year != null) set(Calendar.YEAR, year)
            if (month != null) set(Calendar.MONTH, month - 1)
            if (day != null) set(Calendar.DAY_OF_MONTH, day)
            if (hour != null) set(Calendar.HOUR_OF_DAY, hour)
            if (minute != null) set(Calendar.MINUTE, minute)
            if (second != null) set(Calendar.SECOND, second)
        }

        if(amount != null && amount > BigDecimal.ZERO && year != null && month != null && day != null && hour != null && minute != null && second != null){
            val parallel_check = parallelCheck(year, month, day, hour, minute, second)
            if(parallel_check){
                if (entered_calendar <= today_calendar){
                    val expense = MainFields(currentItemId,
                        amount, year, month, day, hour, minute, second, selected_currency, selected_type, description, operation)
                    mainViewModel.updateExpense(expense)
                    if (operation == "Expense"){
                        Toast.makeText(requireContext(), R.string.expense_updated, Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(requireContext(), R.string.income_updated, Toast.LENGTH_LONG).show()
                    }
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                else{
                    Toast.makeText(requireContext(), R.string.wrong_date, Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(requireContext(), R.string.parallel_date, Toast.LENGTH_LONG).show()
            }

        }
        else{
            Toast.makeText(requireContext(), R.string.fields_not_fill, Toast.LENGTH_LONG).show()
        }
    }

    private fun parallelCheck(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Boolean{

        val is_leap_year = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
        val month31 = setOf(1, 3, 5, 7, 8, 10, 12)
        val month30 = setOf(4, 6, 9, 11)

        if (month !in 1..12) return false
        if (hour !in 0..23 || minute !in 0..59 || second !in 0..59) return false
        return when {
            month == 2 -> {
                if (is_leap_year) day in 1..29 else day in 1..28
            }
            month in month31 -> day in 1..31
            month in month30 -> day in 1..30
            else -> false
        }
    }

}