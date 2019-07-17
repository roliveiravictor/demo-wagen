package com.stonetree.demowagen.features.builtdates.resources.directions

import android.os.Bundle
import androidx.navigation.NavDirections
import com.stonetree.demowagen.R
import com.stonetree.demowagen.constants.DirectionsBundleKey.BUILT_DATES

class BuiltDatesDirections private constructor() {

    private data class ActionBuiltDatesToProductSelection(val date: String) : NavDirections {
        override fun getActionId(): Int = R.id.product_selection

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putString(BUILT_DATES, this.date)
            return result
        }
    }

    companion object {
        fun actionBuiltDatesToProductSelection(date: String): NavDirections = ActionBuiltDatesToProductSelection(date)
    }
}