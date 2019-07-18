package com.stonetree.demowagen.features.cartypes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CarTypesViewModel(
    repository: CarTypesRepository
): ViewModel() {

    var carTypes: MutableLiveData<List<String>> = MutableLiveData()

    var title: MutableLiveData<String> = MutableLiveData()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        viewModelScope.launch {
            repository.setTitle(title)
            repository.saveManufacturer()
            repository.getCarTypes(carTypes)
        }
    }
}