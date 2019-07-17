package com.stonetree.demowagen.features.builtdates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stonetree.demowagen.features.builtdates.resources.repository.BuiltDatesRepository
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import com.stonetree.demowagen.features.cartypes.viewmodel.CarTypesViewModel
import com.stonetree.demowagen.features.manufacturer.model.WKDA

class BuiltDatesViewModelFactory(
    private val carType: String,
    private val repository: BuiltDatesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BuiltDatesViewModel(carType, repository) as T
    }
}