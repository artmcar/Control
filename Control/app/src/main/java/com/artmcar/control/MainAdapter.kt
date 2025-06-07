package com.artmcar.control

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artmcar.control.databinding.MainItemBinding
import com.artmcar.control.db.MainFields

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var expenseList = emptyList<MainFields>()

    class MainViewHolder(val binding : MainItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currentItem = expenseList[position]
    }

    fun setData(expense: List<MainFields>){
        this.expenseList = expense
        notifyDataSetChanged()
    }

}