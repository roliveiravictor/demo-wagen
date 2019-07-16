package com.stonetree.demowagen.cartypes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.cartypes.view.adapter.CarTypesAdapter
import com.stonetree.demowagen.cartypes.viewmodel.CarTypesViewModel
import com.stonetree.demowagen.databinding.ViewCarTypesBinding

class CarTypesView : CoreView() {

    private val vm by lazy {
        return@lazy ViewModelProviders.of(this@CarTypesView).get(CarTypesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewCarTypesBinding.inflate(inflater, viewGroup, false)
        val adapter = CarTypesAdapter()

        bindData(container, adapter)
        bindObservers(vm, adapter)
        return container.root
    }

    private fun bindData(
        container: ViewCarTypesBinding,
        adapter: CarTypesAdapter
    ) {
        container.view = this@CarTypesView
        container.carTypesList.adapter = adapter
    }

    private fun bindObservers(
        vm: CarTypesViewModel,
        adapter: CarTypesAdapter
    ) {
        vm.getCarTypes().observe(this@CarTypesView) { carTypes ->
            adapter.submitList(carTypes)
        }
    }
}
