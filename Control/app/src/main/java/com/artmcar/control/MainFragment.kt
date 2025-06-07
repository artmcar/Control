package com.artmcar.control

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.artmcar.control.databinding.FragmentMainBinding
import com.artmcar.control.db.MainViewModel


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        //RecyclerView
        adapter = MainAdapter()
        val recyclerView = binding.mainRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //MainViewModel
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getAllExpenses.observe(viewLifecycleOwner, Observer { expense ->
            adapter.setData(expense)
        })

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), FullscreenActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }


}