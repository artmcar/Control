package com.artmcar.control.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artmcar.control.databinding.CurrencyItemBinding
import com.artmcar.control.db.currency_rate.EurRate


class EurAdapter : RecyclerView.Adapter<EurAdapter.EurViewHolder>() {
    private var rateList = emptyList<EurRate>()

    class EurViewHolder(val binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: EurRate) {
            binding.currencyDateTv.text = rate.date
            binding.currencyValueTv.text =  rate.value
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EurViewHolder {
        val binding = CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EurViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EurViewHolder, position: Int) {
        holder.bind(rateList[position])
    }

    override fun getItemCount(): Int {
        return rateList.size
    }

    fun setData(newRates: List<EurRate>) {
        this.rateList = newRates
        notifyDataSetChanged()
    }
}