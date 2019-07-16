package com.stonetree.demowagen.features.cartypes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.stonetree.coreview.CoreView
import com.stonetree.coreview.R
import com.stonetree.demowagen.features.cartypes.view.adapter.CarTypesAdapter
import com.stonetree.demowagen.features.cartypes.viewmodel.CarTypesViewModel
import com.stonetree.demowagen.constants.DirectionsBundleKey
import com.stonetree.demowagen.databinding.ViewCarTypesBinding
import com.stonetree.demowagen.features.manufacturer.model.WKDA
import com.stonetree.demowagen.utilities.InjectorUtils

class CarTypesView : CoreView() {

    private val args: CarTypesViewArgs by navArgs()
    private val vm: CarTypesViewModel by viewModels {
        InjectorUtils.provideCarTypesViewModelFactory(args.wkda ?: WKDA())
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewCarTypesBinding.inflate(inflater, viewGroup, false)
        val adapter = CarTypesAdapter()

        setTitle(DirectionsBundleKey.WKDA)
        bindData(container, adapter)
        bindObservers(vm, adapter)
        return container.root
    }

    override fun setTitle(key: String) {
        activity?.let { fragment ->
            fragment.title = ""
            fragment.title = fragment.title.toString().plus(" " + (arguments?.getSerializable(key) as WKDA).name)
        }
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
