package com.artmcar.control.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artmcar.control.R
import com.artmcar.control.databinding.FragmentReadBinding
import com.artmcar.control.db.main.MainFields

class ReadFragment : Fragment() {
    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadBinding.inflate(inflater, container, false)
        val context = binding.root.context
        val item = arguments?.getSerializable("item_data") as? MainFields
        val date_array = binding.root.context.resources.getStringArray(R.array.months)
        val currency_array = binding.root.context.resources.getStringArray(R.array.currency)

        item?.let {
            val is_expense = it.operation == "Expense"
            val date = "${it.day} ${date_array[it.month - 1]} ${it.year}"
            val currency = when(it.currency){
                "RUB" -> currency_array[0]
                "USD" -> currency_array[1]
                "EUR" -> currency_array[2]
                "AMD" -> currency_array[3]
                else -> currency_array[0]
            }
            binding.readDate.text = date
            binding.readAmount.movementMethod = ScrollingMovementMethod()
            binding.readAmount.text = "${it.amount.toPlainString()} $currency"
            binding.readDescription.text = it.description
            binding.readType.text = it.type

            binding.amountColon.text =
                if (is_expense){ context.getString(R.string.amount_expense_colon)}
                else{ context.getString(R.string.amount_income_colon)}

            binding.typeColon.text =
                if (is_expense){ context.getString(R.string.type_expense_colon)}
                else {context.getString(R.string.type_income_colon)}

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.readToolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.readToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


}