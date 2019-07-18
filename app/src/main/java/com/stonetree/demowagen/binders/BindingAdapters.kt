package com.stonetree.demowagen.binders

import android.view.View
import androidx.databinding.BindingAdapter

//TODO - BindAdapter Example
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}