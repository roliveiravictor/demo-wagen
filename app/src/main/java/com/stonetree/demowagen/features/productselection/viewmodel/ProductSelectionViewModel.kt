package com.stonetree.demowagen.features.productselection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.features.productselection.resources.repository.ProductSelectionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ProductSelectionViewModel(repository: ProductSelectionRepository) : ViewModel() {

    var wagen: MutableLiveData<Wagen> = MutableLiveData()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        viewModelScope.launch {
            repository.setWagen(wagen)
        }
    }
}