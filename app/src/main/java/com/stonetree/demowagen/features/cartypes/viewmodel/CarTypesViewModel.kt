package com.stonetree.demowagen.features.cartypes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.data.WKDA
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CarTypesViewModel(
    val wkda: WKDA,
    val repository: CarTypesRepository
): ViewModel() {

    var carTypes: MutableLiveData<List<String>> = MutableLiveData()

    var title: MutableLiveData<String> = MutableLiveData()

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
            repository.saveManufacturer(wkda)
            repository.setTitle(title)
            repository.getCarTypes(carTypes)
        }
    }
}