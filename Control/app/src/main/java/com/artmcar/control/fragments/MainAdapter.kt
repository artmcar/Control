package com.artmcar.control.fragments

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.artmcar.control.activities.FullscreenActivity
import com.artmcar.control.R
import com.artmcar.control.databinding.MainItemBinding
import com.artmcar.control.db.main.MainFields


class MainAdapter(
    private val onItemClick: (MainFields) -> Unit,
    private val onDeleteClick: (MainFields) -> Unit): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var expenseList = emptyList<MainFields>()

    class MainViewHolder(val binding : MainItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: MainFields){
            val date_array = binding.root.context.resources.getStringArray(R.array.months)
            val date = "${currentItem.day} ${date_array[currentItem.month - 1]} ${currentItem.year}"
            val currency_array = binding.root.context.resources.getStringArray(R.array.currency)
            val set_currency = when(currentItem.currency){
                "RUB" -> currency_array[0]
                "USD" -> currency_array[1]
                "EUR" -> currency_array[2]
                else -> currency_array[0]
            }
            binding.dateTv.text = date
            if (currentItem.amount.stripTrailingZeros().scale() <= 0){
                binding.amountTv.text = currentItem.amount.toBigInteger().toString()
            }
            else{
                binding.amountTv.text = currentItem.amount.toPlainString()
            }
            binding.currencyTv.text = set_currency

            val operation_color = if(currentItem.operation == "Expense"){
                ContextCompat.getColor(binding.root.context, R.color.expense_view_color)
            }else{ContextCompat.getColor(binding.root.context, R.color.income_view_color)}
            val drawable = binding.operationView.background
            if (drawable is GradientDrawable) {
                drawable.mutate()
                drawable.setColor(operation_color)
            }

        }
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

        holder.binding.editButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FullscreenActivity::class.java).apply {
                putExtra("fragment_to_open", "update")
                putExtra("item_data", currentItem)
            }
            context.startActivity(intent)
        }

        holder.binding.deleteButton.setOnClickListener {
            onDeleteClick(currentItem)
        }

        holder.binding.root.setOnClickListener{
            onItemClick(currentItem)
        }

        holder.bind(currentItem)

    }

    fun setData(expense: List<MainFields>){
        this.expenseList = expense
        notifyDataSetChanged()
    }

}