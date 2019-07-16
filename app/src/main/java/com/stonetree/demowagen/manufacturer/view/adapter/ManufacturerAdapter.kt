package com.stonetree.demowagen.manufacturer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stonetree.demowagen.R
import com.stonetree.demowagen.databinding.ListItemManufacturerBinding
import com.stonetree.demowagen.manufacturer.resources.directions.ManufacturerDirections

class ManufacturerAdapter : ListAdapter<String, ManufacturerAdapter.ViewHolder>(ManufacturerDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { manufacturer ->
            with(holder) {
                itemView.tag = position
                bind(createOnClickListener(manufacturer), manufacturer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_manufacturer, parent, false
            )
        )
    }

    private fun createOnClickListener(manufacturer: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = ManufacturerDirections.actionManufacturerToCarTypes(manufacturer)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemManufacturerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, manufacturer: String) {
            with(binding) {
                this.listener = listener
                this.manufacturer = manufacturer
            }
        }
    }
}

private class ManufacturerDiffCallback : DiffUtil.ItemCallback<String>() {

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