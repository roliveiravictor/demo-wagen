package com.stonetree.coreview

import androidx.fragment.app.Fragment

open class CoreView : Fragment(), CoreViewInterface {

    override fun setTitle(key: String) {
        activity?.let { fragment ->
            fragment.title = arguments?.getString(key) ?: getString(R.string.app_name)
        }
    }

}