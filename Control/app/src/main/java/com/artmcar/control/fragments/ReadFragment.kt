package com.artmcar.control.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.artmcar.control.R
import com.artmcar.control.databinding.FragmentReadBinding
import com.artmcar.control.db.main.MainFields
import com.artmcar.control.db.main.MainViewModel

class ReadFragment : Fragment() {
    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private var item: MainFields? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val context = binding.root.context
        item = arguments?.getSerializable("item_data") as? MainFields
        val date_array = context.resources.getStringArray(R.array.months)
        val currency_array = context.resources.getStringArray(R.array.currency)

        item?.let {
            val is_expense = it.operation == "Expense"
            val date = "${it.day} ${date_array[it.month - 1]} ${it.year}"
            val time = String.format("%02d:%02d:%02d", it.hour, it.minute, it.second)
            val currency = when(it.currency){
                "RUB" -> currency_array[0]
                "USD" -> currency_array[1]
                "EUR" -> currency_array[2]
                "AMD" -> currency_array[3]
                else -> currency_array[0]
            }
            binding.readDate.text = date
            binding.readTime.text = time
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
        binding.readToolbar.inflateMenu(R.menu.read_menu)
        binding.readToolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.readToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.readToolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_delete_from_read -> {
                    item?.let {
                        deleteExpense(requireContext(), it)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun deleteExpense(context: Context, mainFields: MainFields){
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton(R.string.allert_yes){ _, _ ->
            mainViewModel.deleteExpense(mainFields)
            requireActivity().onBackPressedDispatcher.onBackPressed()
            val message_for_one = context.getString(R.string.successfully_deleted)
            Toast.makeText(context, message_for_one, Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton(R.string.allert_no){ _, _->}
        builder.setTitle(R.string.delete_question)
        builder.setMessage(R.string.are_you_sure_to_delete)
        builder.create().show()
    }


}