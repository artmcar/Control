package com.artmcar.control.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.artmcar.control.databinding.FragmentEurBinding
import com.artmcar.control.db.currency_rate.CurrencyDatabase
import com.artmcar.control.db.currency_rate.EurViewModel
import com.artmcar.control.db.currency_rate.EurViewModelFactory


class EurFragment : Fragment() {
    private var _binding: FragmentEurBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EurViewModel
    private lateinit var adapter: EurAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEurBinding.inflate(inflater, container, false)

        val eurRateDao = CurrencyDatabase.getDatabase(requireContext()).eurRateDao()
        val factory = EurViewModelFactory(eurRateDao)
        viewModel = ViewModelProvider(this, factory).get(EurViewModel::class.java)

        adapter = EurAdapter()
        binding.eurRecyclerView.adapter = adapter
        binding.eurRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.eurRates.observe(viewLifecycleOwner) { rates ->
            adapter.setData(rates)
        }
        viewModel.loadRates()

        return binding.root
    }

}