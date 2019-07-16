package com.stonetree.demowagen.features.cartypes.resources.directions

import android.os.Bundle
import androidx.navigation.NavDirections
import com.stonetree.demowagen.R
import com.stonetree.demowagen.constants.DirectionsBundleKey

class CarTypesDirections private constructor() {

    private data class ActionCarTypesToBuiltDates(val carType: String) : NavDirections {
        override fun getActionId(): Int = R.id.built_dates_view

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putString(DirectionsBundleKey.CAR_TYPES, this.carType)
            return result
        }
    }

    companion object {
        fun actionCarTypesToBuiltDates(manufacturer: String): NavDirections = ActionCarTypesToBuiltDates(manufacturer)
    }
}