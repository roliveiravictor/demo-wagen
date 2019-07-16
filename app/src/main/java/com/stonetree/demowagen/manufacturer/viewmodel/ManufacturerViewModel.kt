package com.stonetree.demowagen.manufacturer.viewmodel

import androidx.lifecycle.*
import com.stonetree.demowagen.manufacturer.resources.repository.ManufacturerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch




class ManufacturerViewModel : ViewModel() {

    private val repository = ManufacturerRepository.getInstance()

    var manufacturers: MutableLiveData<List<String>> = MutableLiveData()

    fun getManufacturers(): LiveData<List<String>> {
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
