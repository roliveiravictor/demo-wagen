package com.stonetree.demowagen.cartypes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stonetree.demowagen.R
import com.stonetree.demowagen.cartypes.resources.directions.CarTypesDirections
import com.stonetree.demowagen.databinding.ListItemCarTypeBinding

class CarTypesAdapter : ListAdapter<String, CarTypesAdapter.ViewHolder>(CarTypesDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { carType ->
            with(holder) {
                itemView.tag = position
                bind(createOnClickListener(carType), carType)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_car_type, parent, false
            )
        )
    }

    private fun createOnClickListener(carType: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = CarTypesDirections.actionCarTypesToBuiltDates(carType)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemCarTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, carType: String) {
            with(binding) {
                this.listener = listener
                this.carType = carType
            }
        }
    }
}

private class CarTypesDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}