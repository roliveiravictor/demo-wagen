package com.stonetree.demowagen.cartypes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.databinding.ViewCarTypesBinding

class CarTypesView : CoreView() {

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewCarTypesBinding.inflate(inflater, viewGroup, false)
        return container.root
    }
}
