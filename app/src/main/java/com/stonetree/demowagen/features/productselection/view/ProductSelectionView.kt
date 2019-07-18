package com.stonetree.demowagen.features.productselection.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.R
import com.stonetree.demowagen.databinding.ViewProdructSelectionBinding
import com.stonetree.demowagen.features.productselection.viewmodel.ProductSelectionViewModel
import com.stonetree.demowagen.utilities.InjectorUtils

class ProductSelectionView : CoreView() {

    private val vm: ProductSelectionViewModel by viewModels {
        InjectorUtils.provideProductSelectionViewModelFactory(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewProdructSelectionBinding.inflate(inflater, viewGroup, false)
        bindData(container)
        bindObservers(container)
        return container.root
    }

    private fun bindData(
        container: ViewProdructSelectionBinding
    ) {
        container.view = this@ProductSelectionView
    }

    private fun bindObservers(container: ViewProdructSelectionBinding) {
        activity?.title = getString(R.string.product_purchased)

        vm.wagen.observe(viewLifecycleOwner) { wagen ->
            container.manufacturer.text = wagen.name
            container.carType.text = wagen.carType
            container.builtDate.text = wagen.builtDate
        }
    }
}