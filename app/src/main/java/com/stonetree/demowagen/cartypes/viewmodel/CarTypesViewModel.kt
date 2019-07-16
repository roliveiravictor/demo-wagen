package com.stonetree.demowagen.cartypes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.cartypes.resources.repository.CarTypesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CarTypesViewModel : ViewModel() {

    private val repository = CarTypesRepository.getInstance()

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
            repository.getCarTypes(carTypes)
        }
    }
}