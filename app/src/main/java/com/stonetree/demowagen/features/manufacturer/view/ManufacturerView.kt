package com.stonetree.demowagen.features.manufacturer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.databinding.ViewManufacturerBinding
import com.stonetree.demowagen.features.manufacturer.view.adapter.ManufacturerAdapter
import com.stonetree.demowagen.features.manufacturer.viewmodel.ManufacturerViewModel

class ManufacturerView : CoreView() {

    private val vm by lazy {
        return@lazy ViewModelProviders.of(this@ManufacturerView).get(ManufacturerViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewManufacturerBinding.inflate(inflater, viewGroup, false)
        val adapter = ManufacturerAdapter()

        bindData(container, adapter)
        bindObservers(container)
        return container.root
    }

    private fun bindData(
        container: ViewManufacturerBinding,
        adapter: ManufacturerAdapter
    ) {
        container.view = this@ManufacturerView
        container.manufacturerList.adapter = adapter
    }

    private fun bindObservers(container: ViewManufacturerBinding) {
        vm.manufacturers.observe(viewLifecycleOwner) { manufacturers ->
            container.manufacturerList.visibility = View.VISIBLE
            container.loading.visibility = View.GONE
            val adapter = container.manufacturerList.adapter as ManufacturerAdapter
            adapter.submitList(manufacturers)
        }
//        vm.hasManufacturers.observe(viewLifecycleOwner) { hasManufacturers ->
//            if (hasManufacturers) {
//                container.manufacturerList.visibility = View.VISIBLE
//                container.loading.visibility = View.GONE
//            } else {
//                container.manufacturerList.visibility = View.GONE
//                container.loading.visibility = View.VISIBLE
//            }
//        }
    }
}
