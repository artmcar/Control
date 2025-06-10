package com.artmcar.control.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.artmcar.control.databinding.FragmentUsdBinding
import com.artmcar.control.db.currency_rate.CurrencyDatabase
import com.artmcar.control.db.currency_rate.UsdViewModel
import com.artmcar.control.db.currency_rate.UsdViewModelFactory


class UsdFragment : Fragment() {
    private var _binding: FragmentUsdBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UsdViewModel
    private lateinit var adapter: UsdAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsdBinding.inflate(inflater, container, false)

        val usdRateDao = CurrencyDatabase.getDatabase(requireContext()).usdRateDao()
        val factory = UsdViewModelFactory(usdRateDao)
        viewModel = ViewModelProvider(this, factory).get(UsdViewModel::class.java)

        adapter = UsdAdapter()
        binding.usdRecyclerView.adapter = adapter
        binding.usdRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.usdRates.observe(viewLifecycleOwner) { rates ->
            adapter.setData(rates)
        }
        viewModel.loadRates()

        return binding.root
    }

}