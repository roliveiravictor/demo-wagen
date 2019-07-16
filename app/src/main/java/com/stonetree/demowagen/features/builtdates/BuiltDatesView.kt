package com.stonetree.demowagen.features.builtdates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.constants.DirectionsBundleKey.CAR_TYPES
import com.stonetree.demowagen.databinding.ViewCarTypesBinding

class BuiltDatesView : CoreView() {

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewCarTypesBinding.inflate(inflater, viewGroup, false)

        setTitle(CAR_TYPES)

        return container.root
    }

    override fun setTitle(key: String) {
        activity?.let { fragment ->
            fragment.title = fragment.title.toString().plus(" " + arguments?.getString(key))
        }
    }
}
