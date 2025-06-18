package com.artmcar.control.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.artmcar.control.R
import com.artmcar.control.databinding.FragmentStatisticsBinding
import com.artmcar.control.db.main.MainViewModel
import java.math.BigDecimal
import java.math.RoundingMode


class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rubExpensesAmountTv.movementMethod = ScrollingMovementMethod()
        binding.rubIncomesAmountTv.movementMethod = ScrollingMovementMethod()
        binding.rubDifferenceAmountTv.movementMethod = ScrollingMovementMethod()

        binding.usdExpensesAmountTv.movementMethod = ScrollingMovementMethod()
        binding.usdIncomesAmountTv.movementMethod = ScrollingMovementMethod()
        binding.usdDifferenceAmountTv.movementMethod = ScrollingMovementMethod()

        binding.eurExpensesAmountTv.movementMethod = ScrollingMovementMethod()
        binding.eurIncomesAmountTv.movementMethod = ScrollingMovementMethod()
        binding.eurDifferenceAmountTv.movementMethod = ScrollingMovementMethod()

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.rubExpenses.observe(viewLifecycleOwner) {
            binding.rubExpensesAmountTv.text = format(it, getString(R.string.rub))
            updateDifference()
        }
        viewModel.rubIncomes.observe(viewLifecycleOwner) {
            binding.rubIncomesAmountTv.text = format(it, getString(R.string.rub))
            updateDifference()
        }

        viewModel.usdExpenses.observe(viewLifecycleOwner) {
            binding.usdExpensesAmountTv.text = format(it, getString(R.string.usd))
            updateDifference()
        }
        viewModel.usdIncomes.observe(viewLifecycleOwner) {
            binding.usdIncomesAmountTv.text = format(it, getString(R.string.usd))
            updateDifference()
        }

        viewModel.eurExpenses.observe(viewLifecycleOwner) {
            binding.eurExpensesAmountTv.text = format(it, getString(R.string.eur))
            updateDifference()
        }
        viewModel.eurIncomes.observe(viewLifecycleOwner) {
            binding.eurIncomesAmountTv.text = format(it, getString(R.string.eur))
            updateDifference()
        }
    }
    private fun updateDifference() {
        val rub = (viewModel.rubIncomes.value ?: BigDecimal.ZERO)
            .subtract(viewModel.rubExpenses.value ?: BigDecimal.ZERO)
        val usd = (viewModel.usdIncomes.value ?: BigDecimal.ZERO)
            .subtract(viewModel.usdExpenses.value ?: BigDecimal.ZERO)
        val eur = (viewModel.eurIncomes.value ?: BigDecimal.ZERO)
            .subtract(viewModel.eurExpenses.value ?: BigDecimal.ZERO)

        updateDifferenceTextView(binding.rubDifferenceAmountTv, rub, getString(R.string.rub))
        updateDifferenceTextView(binding.usdDifferenceAmountTv, usd, getString(R.string.usd))
        updateDifferenceTextView(binding.eurDifferenceAmountTv, eur, getString(R.string.eur))
    }

    private fun format(value: BigDecimal?, currency: String): String {
        return "${(value ?: BigDecimal.ZERO).setScale(2, RoundingMode.HALF_EVEN).toPlainString()} $currency"
    }

    private fun updateDifferenceTextView(textView: TextView, value: BigDecimal, currency: String) {
        textView.text = format(value, currency)

        val colorRes = if (value >= BigDecimal.ZERO) R.color.currency_green else R.color.currency_red
        textView.setTextColor(ContextCompat.getColor(requireContext(), colorRes))
    }
}