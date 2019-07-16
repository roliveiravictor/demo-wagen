package com.stonetree.demowagen.features.manufacturer.resources.directions

import android.os.Bundle
import androidx.navigation.NavDirections
import com.stonetree.demowagen.R
import com.stonetree.demowagen.constants.DirectionsBundleKey
import com.stonetree.demowagen.features.manufacturer.model.WKDA

class ManufacturerDirections private constructor() {

    private data class ActionManufacturerToCarTypes(val wkda: WKDA) : NavDirections {
        override fun getActionId(): Int = R.id.car_types_view

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putSerializable(DirectionsBundleKey.WKDA, this.wkda)
            return result
        }
    }

    companion object {
        fun actionManufacturerToCarTypes(wkda: WKDA): NavDirections = ActionManufacturerToCarTypes(wkda)
    }
}