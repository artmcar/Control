package com.artmcar.control.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.artmcar.control.activities.FullscreenActivity
import com.artmcar.control.R
import com.artmcar.control.currencies.CurrencyService
import com.artmcar.control.databinding.FragmentMainBinding
import com.artmcar.control.db.main.MainFields
import com.artmcar.control.db.main.MainViewModel
import com.artmcar.control.db.currency_rate.CurrencyDatabase
import com.artmcar.control.db.currency_rate.CurrencyRepository
import com.artmcar.control.db.currency_rate.CurrencyViewModel
import com.artmcar.control.db.currency_rate.CurrencyViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    private var selectedSortDate = 0
    private var selectedSortAmount = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        //RecyclerView
        adapter = MainAdapter(
            onItemClick = { item ->
                val intent = Intent(requireContext(), FullscreenActivity::class.java).apply {
                    putExtra("fragment_to_open", "read")
                    putExtra("item_data", item)
                }
                startActivity(intent)
            },
            onDeleteClick = { itemToDelete ->
                deleteExpense(requireContext(), itemToDelete)
            }
        )

        val recyclerView = binding.mainRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //MainViewModel
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getAllExpenses.observe(viewLifecycleOwner, Observer { expense ->
            adapter.setData(expense)
            checkIfEmpty()
        })

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), FullscreenActivity::class.java)
            intent.putExtra("fragment_to_open", "add")
            startActivity(intent)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = SecurePrefsUtil.getSecurePrefs(requireContext())
        val pin = prefs.getString("pin", null)
        val lockedNextLaunch = prefs.getBoolean("locked_next_launch", false)

        binding.mainToolbar.inflateMenu(R.menu.main_menu)
        val lockItem = binding.mainToolbar.menu.findItem(R.id.action_unlock)
        lockItem?.icon = ContextCompat.getDrawable(
            requireContext(),
            if (lockedNextLaunch) R.drawable.ic_lock else R.drawable.ic_unlock
        )
        binding.mainToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete_all -> {
                    deleteAllExpenses()
                    true
                }
                R.id.action_sort_by_amount -> {
                    if (selectedSortAmount == 0){
                        getByAmount()
                        selectedSortAmount = 1
                    }
                    else{
                        getByBackAmount()
                        selectedSortAmount = 0
                    }
                    true
                }
                R.id.action_sort_by_date -> {
                    if (selectedSortDate == 0){
                        getByDate()
                        selectedSortDate = 1
                    }
                    else{
                        getByBackDate()
                        selectedSortDate = 0
                    }
                    true
                }
                R.id.action_unlock -> {
                    if (pin == null) {
                        findNavController().navigate(R.id.action_mainFragment_to_pinSetupFragment)
                    } else {
                        val newLockedState = !lockedNextLaunch
                        SecurePrefsUtil.setLockedNextLaunch(requireContext(), newLockedState)
                        it.icon = ContextCompat.getDrawable(
                            requireContext(),
                            if (newLockedState) R.drawable.ic_lock else R.drawable.ic_unlock
                        )
                    }
                    true
                }
                else -> false
            }
        }

        //Rates
        val db = CurrencyDatabase.getDatabase(requireContext())
        val service = CurrencyService.create()
        val repository = CurrencyRepository(service, db)
        val factory = CurrencyViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(CurrencyViewModel::class.java)

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("ru"))
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        val dates = listOf(today.format(formatter), yesterday.format(formatter))

        viewModel.loadRatesForDates(dates)
        lifecycleScope.launchWhenStarted {
            viewModel.errorFlow.collect { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun getByBackDate() {
        mainViewModel.getByBackDate.observe(viewLifecycleOwner){ sorted ->
            adapter.setData(sorted)
        }
    }

    private fun getByDate() {
        mainViewModel.getByDate.observe(viewLifecycleOwner){ sorted ->
            adapter.setData(sorted)
        }
    }

    private fun getByBackAmount() {
        mainViewModel.getByBackAmount.observe(viewLifecycleOwner){ sorted ->
            adapter.setData(sorted)
        }
    }

    private fun getByAmount() {
        mainViewModel.getByAmount.observe(viewLifecycleOwner){ sorted ->
            adapter.setData(sorted)
        }
    }

    private fun deleteExpense(context: Context, mainFields: MainFields){
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton(R.string.allert_yes){ _, _ ->
            mainViewModel.deleteExpense(mainFields)
            val message_for_one = context.getString(R.string.successfully_deleted)
            Toast.makeText(context, message_for_one, Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton(R.string.allert_no){ _, _->}
        builder.setTitle(R.string.delete_question)
        builder.setMessage(R.string.are_you_sure_to_delete)
        builder.create().show()
    }

    private fun deleteAllExpenses() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.allert_yes){ _, _->
            mainViewModel.deleteAllExpenses()
            Toast.makeText(requireContext(), R.string.successfully_deleted_everything, Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton(R.string.allert_no){ _, _->}
        builder.setTitle(R.string.delete_everything)
        builder.setMessage(R.string.are_you_sure_to_delete_everything)
        builder.create().show()
    }

    private fun checkIfEmpty(){
        if(adapter.itemCount == 0){
            binding.emptyIv.visibility = View.VISIBLE
            binding.emptyTv.visibility = View.VISIBLE
            binding.mainRecyclerView.visibility = View.GONE
        }
        else{
            binding.emptyIv.visibility = View.GONE
            binding.emptyTv.visibility = View.GONE
            binding.mainRecyclerView.visibility = View.VISIBLE
        }
    }


}