package com.stonetree.demowagen.features.builtdates.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stonetree.demowagen.R
import com.stonetree.demowagen.databinding.ListItemBuiltDatesBinding
import com.stonetree.demowagen.features.builtdates.resources.directions.BuiltDatesDirections

class BuiltDatesAdapter : ListAdapter<String, BuiltDatesAdapter.ViewHolder>(BuiltDatesDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { builtDate ->
            with(holder) {
                itemView.tag = position
                bind(createOnClickListener(builtDate), builtDate, getRowColor(position))
            }
        }
    }

    private fun getRowColor(position: Int): Int {
        return if (position % 2 == 0) Color.LTGRAY else Color.WHITE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_built_dates, parent, false
            )
        )
    }

    private fun createOnClickListener(builtDate: String): View.OnClickListener {
        return View.OnClickListener { view ->
            val direction = BuiltDatesDirections.actionBuiltDatesToProductSelection(builtDate)
            view.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemBuiltDatesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, builtDate: String, color: Int) {
            with(binding) {
                this.listener = listener
                this.builtDate = builtDate
                this.color = color
            }
        }
    }
}

private class BuiltDatesDiffCallback : DiffUtil.ItemCallback<String>() {

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