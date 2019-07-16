package com.stonetree.demowagen.features.builtdates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stonetree.demowagen.databinding.ViewCarTypesBinding

class BuiltDatesView : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val container = ViewCarTypesBinding.inflate(inflater, viewGroup, false)
        return container.root
    }

}
