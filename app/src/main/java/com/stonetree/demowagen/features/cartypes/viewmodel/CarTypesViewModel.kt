package com.stonetree.demowagen.features.cartypes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import com.stonetree.demowagen.features.manufacturer.model.WKDA
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CarTypesViewModel(
    private val wkda: WKDA,
    repository: CarTypesRepository
): ViewModel() {

    var carTypes: MutableLiveData<List<String>> = MutableLiveData()

    fun getCarTypes(): LiveData<List<String>> {
        return carTypes
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        viewModelScope.launch {
            repository.getCarTypes(wkda.id, carTypes)
        }
    }
}