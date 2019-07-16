package com.stonetree.demowagen.features.manufacturer.view.adapter

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
import com.stonetree.demowagen.features.manufacturer.model.WKDA
import com.stonetree.demowagen.features.manufacturer.resources.directions.ManufacturerDirections

class ManufacturerAdapter : ListAdapter<WKDA, ManufacturerAdapter.ViewHolder>(ManufacturerDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { wkda ->
            with(holder) {
                itemView.tag = position
                bind(createOnClickListener(wkda), wkda.name)
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

    private fun createOnClickListener(wkda: WKDA): View.OnClickListener {
        return View.OnClickListener {
            val direction = ManufacturerDirections.actionManufacturerToCarTypes(wkda)
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

private class ManufacturerDiffCallback : DiffUtil.ItemCallback<WKDA>() {

    override fun areItemsTheSame(
        oldItem: WKDA,
        newItem: WKDA
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: WKDA,
        newItem: WKDA
    ): Boolean {
        return oldItem.id == newItem.id
    }
}