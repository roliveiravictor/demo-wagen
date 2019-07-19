package com.stonetree.demowagen.features.productselection.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.R
import com.stonetree.demowagen.databinding.ViewProdructSelectionBinding
import com.stonetree.demowagen.features.productselection.viewmodel.ProductSelectionViewModel
import com.stonetree.demowagen.utilities.InjectorUtils

class ProductSelectionView : CoreView() {

    private val args: ProductSelectionViewArgs by navArgs()
    private val vm: ProductSelectionViewModel by viewModels {
        InjectorUtils.provideProductSelectionViewModelFactory(requireActivity(), args.builtDates)
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewProdructSelectionBinding.inflate(inflater, viewGroup, false)
        bindData(container)
        bindObservers(container)
        bindBackPressed()
        bindTitle()
        return container.root
    }

    private fun bindData(
        container: ViewProdructSelectionBinding
    ) {
        container.view = this@ProductSelectionView
    }

    override fun onBackPressed(): OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().currentDestination?.id.apply {
                    if(this == R.id.product_selection_view)
                        vm.clearCurrentStack()
                }
                findNavController().navigate(R.id.manufacturer_view)
            }
        }
    }

    private fun bindBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed())
    }

    private fun bindObservers(container: ViewProdructSelectionBinding) {
        vm.wagen.observe(viewLifecycleOwner) { wagen ->
            container.wagen = wagen
        }
    }

    private fun bindTitle() {
        activity?.title = getString(R.string.product_purchased)
    }
}