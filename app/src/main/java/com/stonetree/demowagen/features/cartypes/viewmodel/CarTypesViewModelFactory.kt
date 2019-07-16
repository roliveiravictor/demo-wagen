package com.stonetree.demowagen.features.cartypes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import com.stonetree.demowagen.features.manufacturer.model.WKDA

class CarTypesViewModelFactory(
    private val wkda: WKDA,
    private val repository: CarTypesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CarTypesViewModel(wkda, repository) as T
    }
}