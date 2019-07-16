package com.stonetree.demowagen.features.manufacturer.viewmodel

import androidx.lifecycle.*
import com.stonetree.demowagen.features.manufacturer.model.WKDA
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ManufacturerViewModel : ViewModel() {

    private val repository = ManufacturerRepository.getInstance()

    var manufacturers: MutableLiveData<List<WKDA>> = MutableLiveData()

    fun getManufacturers(): LiveData<List<WKDA>> {
        return manufacturers
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        viewModelScope.launch {
            repository.getManufacturers(manufacturers)
        }
    }
}
