package com.stonetree.demowagen.features.productselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stonetree.coreview.CoreView
import com.stonetree.demowagen.constants.DirectionsBundleKey.BUILT_DATES
import com.stonetree.demowagen.databinding.ViewProdructSelectionBinding

class ProductSelectionView : CoreView() {

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewProdructSelectionBinding.inflate(inflater, viewGroup, false)
        return container.root
    }
}