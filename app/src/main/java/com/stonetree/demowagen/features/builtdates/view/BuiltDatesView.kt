package com.stonetree.demowagen.features.builtdates.view

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
import com.stonetree.demowagen.databinding.ViewBuiltDatesBinding
import com.stonetree.demowagen.features.builtdates.view.adapter.BuiltDatesAdapter
import com.stonetree.demowagen.features.builtdates.viewmodel.BuiltDatesViewModel
import com.stonetree.demowagen.utilities.InjectorUtils

class BuiltDatesView : CoreView() {

    private val args: BuiltDatesViewArgs by navArgs()
    private val vm: BuiltDatesViewModel by viewModels {
        InjectorUtils.provideBuiltDatesViewModelFactory(requireContext(), args.mainType)
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewBuiltDatesBinding.inflate(inflater, viewGroup, false)
        val adapter = BuiltDatesAdapter()

        bindData(container, adapter)
        bindObservers(container, adapter)
        bindBackPressed()
        return container.root
    }

    private fun bindBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed())
    }

    override fun onBackPressed(): OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().currentDestination?.id.apply {
                    if(this == R.id.built_dates_view)
                        vm.clearCurrentStack()
                }
                findNavController().navigate(R.id.car_types_view)
            }
        }
    }

    private fun bindData(
        container: ViewBuiltDatesBinding,
        adapter: BuiltDatesAdapter
    ) {
        container.view = this@BuiltDatesView
        container.builtDatesList.adapter = adapter
    }

    private fun bindObservers(container: ViewBuiltDatesBinding, adapter: BuiltDatesAdapter) {
        vm.builtDates.observe(viewLifecycleOwner) { builtDates ->
            container.hasBuiltDates = !builtDates.isNullOrEmpty()
            adapter.submitList(builtDates)
        }

        vm.title.observe(viewLifecycleOwner) { title ->
            activity?.title = title
        }
    }
}
