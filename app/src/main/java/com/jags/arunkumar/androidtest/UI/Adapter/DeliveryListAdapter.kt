package com.jags.arunkumar.androidtest.UI.Adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jags.arunkumar.androidtest.BR
import com.jags.arunkumar.androidtest.R
import com.jags.arunkumar.androidtest.data.DeliveryData

class DeliveryListAdapter(val data: List<DeliveryData>) : RecyclerView.Adapter<DeliveryListHolder>() {
    override fun onBindViewHolder(holder: DeliveryListHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.delivery_data_item, parent, false)

        return DeliveryListHolder(binding)
    }

    override fun getItemCount(): Int = data.size
}

class DeliveryListHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Any) {
        binding.setVariable(BR.data, data)
        binding.executePendingBindings()
    }
}
