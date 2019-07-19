package com.stonetree.demowagen.features.manufacturer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.R
import com.stonetree.demowagen.databinding.ViewManufacturerBinding
import com.stonetree.demowagen.features.manufacturer.view.adapter.ManufacturerAdapter
import com.stonetree.demowagen.features.manufacturer.viewmodel.ManufacturerViewModel
import com.stonetree.demowagen.utilities.InjectorUtils

class ManufacturerView : CoreView() {

    private val vm: ManufacturerViewModel by viewModels {
        InjectorUtils.provideManufacturerViewModelFactory(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewManufacturerBinding.inflate(inflater, viewGroup, false)
        val adapter = ManufacturerAdapter()

        bindData(container, adapter)
        bindObservers(container)
        bindBackPressed()
        return container.root
    }

    private fun bindBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed())
    }

    override fun onBackPressed(): OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
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
            adjustVisibility(container)
            val adapter = container.manufacturerList.adapter as ManufacturerAdapter
            adapter.submitList(manufacturers)
        }

        vm.title.observe(viewLifecycleOwner) { title ->
            if(title.isBlank())
                activity?.title = getString(R.string.app_name)
            else
                activity?.title = title
        }
    }

    private fun adjustVisibility(container: ViewManufacturerBinding) {
        container.manufacturerList.visibility = View.VISIBLE
        container.loading.visibility = View.GONE
    }
}