package com.stonetree.demowagen.features.builtdates.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.constants.DirectionsBundleKey.BUILT_DATES
import com.stonetree.demowagen.constants.DirectionsBundleKey.CAR_TYPES
import com.stonetree.demowagen.databinding.ViewBuiltDatesBinding
import com.stonetree.demowagen.features.builtdates.view.adapter.BuiltDatesAdapter
import com.stonetree.demowagen.features.builtdates.viewmodel.BuiltDatesViewModel
import com.stonetree.demowagen.utilities.InjectorUtils

class BuiltDatesView : CoreView() {

    private val args: BuiltDatesViewArgs by navArgs()
    private val vm: BuiltDatesViewModel by viewModels {
        InjectorUtils.provideBuiltDatesViewModelFactory(args.mainType)
    }

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewBuiltDatesBinding.inflate(inflater, viewGroup, false)
        val adapter = BuiltDatesAdapter()

        setTitle(CAR_TYPES)
        bindData(container, adapter)
        bindObservers(adapter)
        return container.root
    }

    override fun setTitle(key: String) {
        activity?.let { fragment ->
            fragment.title = fragment.title.toString().plus(" " + arguments?.getString(key))
        }
    }

    private fun bindData(
        container: ViewBuiltDatesBinding,
        adapter: BuiltDatesAdapter
    ) {
        container.view = this@BuiltDatesView
        container.builtDatesList.adapter = adapter
    }

    private fun bindObservers(adapter: BuiltDatesAdapter) {
        vm.builtDates.observe(viewLifecycleOwner) { builtDates ->
            adapter.submitList(builtDates)
        }
    }
}
