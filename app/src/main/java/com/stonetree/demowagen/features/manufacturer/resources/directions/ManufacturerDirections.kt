package com.stonetree.demowagen.features.manufacturer.resources.directions

import android.os.Bundle
import androidx.navigation.NavDirections
import com.stonetree.demowagen.R
import com.stonetree.demowagen.constants.DirectionsBundleKey

class ManufacturerDirections private constructor() {

    private data class ActionManufacturerToCarTypes(val manufacturer: String) : NavDirections {
        override fun getActionId(): Int = R.id.car_types_view

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putString(DirectionsBundleKey.MANUFACTURER, this.manufacturer)
            return result
        }
    }

    companion object {
        fun actionManufacturerToCarTypes(manufacturer: String): NavDirections = ActionManufacturerToCarTypes(manufacturer)
    }
}