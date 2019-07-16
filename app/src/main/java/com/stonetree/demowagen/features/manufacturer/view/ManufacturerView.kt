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
        bindObservers(vm, adapter)
        return container.root
    }

    private fun bindData(
        container: ViewManufacturerBinding,
        adapter: ManufacturerAdapter
    ) {
        container.view = this@ManufacturerView
        container.manufacturerList.adapter = adapter
    }

    private fun bindObservers(
        vm: ManufacturerViewModel,
        adapter: ManufacturerAdapter
    ) {
        vm.getManufacturers().observe(this@ManufacturerView) { manufacturers ->
            adapter.submitList(manufacturers)
        }
    }
}