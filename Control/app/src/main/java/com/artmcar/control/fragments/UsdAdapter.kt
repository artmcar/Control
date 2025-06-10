package com.artmcar.control.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artmcar.control.databinding.CurrencyItemBinding
import com.artmcar.control.db.currency_rate.UsdRate


class UsdAdapter : RecyclerView.Adapter<UsdAdapter.UsdViewHolder>() {
    private var rateList = emptyList<UsdRate>()

    class UsdViewHolder(val binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: UsdRate) {
            binding.currencyDateTv.text = rate.date
            binding.currencyValueTv.text =  rate.value
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsdViewHolder {
        val binding = CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsdViewHolder, position: Int) {
        holder.bind(rateList[position])
    }

    override fun getItemCount(): Int {
        return rateList.size
    }

    fun setData(newRates: List<UsdRate>) {
        this.rateList = newRates
        notifyDataSetChanged()
    }
}