package com.stonetree.demowagen.features.manufacturer.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import com.stonetree.demowagen.features.manufacturer.viewmodel.ManufacturerViewModel

class ManufacturerViewModelFactory(
    private val repository: ManufacturerRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ManufacturerViewModel(repository) as T
    }
}