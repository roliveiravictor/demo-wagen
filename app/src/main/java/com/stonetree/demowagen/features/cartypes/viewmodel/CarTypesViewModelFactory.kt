package com.stonetree.demowagen.features.cartypes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository

class CarTypesViewModelFactory(
    private val manufacturerId: String,
    private val repository: CarTypesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CarTypesViewModel(manufacturerId, repository) as T
    }
}