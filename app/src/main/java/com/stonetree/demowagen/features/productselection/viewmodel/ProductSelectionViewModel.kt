package com.stonetree.demowagen.features.productselection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.data.wagen.Wagen
import com.stonetree.demowagen.features.productselection.resources.repository.ProductSelectionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ProductSelectionViewModel(val builtDates: String, val repository: ProductSelectionRepository) : ViewModel() {

    var wagen: MutableLiveData<Wagen> = MutableLiveData()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun clearCurrentStack() {
        viewModelScope.launch {
            repository.clear()
        }
    }

    init {
        viewModelScope.launch {
            repository.saveBuiltDate(builtDates)
            repository.loadWagen()
            repository.setWagen(wagen)
        }
    }
}