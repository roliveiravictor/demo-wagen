package com.stonetree.demowagen.features.productselection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stonetree.demowagen.features.productselection.resources.repository.ProductSelectionRepository

class ProductSelectionViewModelFactory(
    private val repository: ProductSelectionRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductSelectionViewModel(repository) as T
    }
}