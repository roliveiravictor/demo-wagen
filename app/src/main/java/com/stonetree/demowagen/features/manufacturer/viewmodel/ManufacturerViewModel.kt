package com.stonetree.demowagen.features.manufacturer.viewmodel

import androidx.lifecycle.*
import com.stonetree.demowagen.data.WKDA
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ManufacturerViewModel(repository: ManufacturerRepository) : ViewModel() {

    var manufacturers: MutableLiveData<List<WKDA>> = MutableLiveData()

    var title: MutableLiveData<String> = MutableLiveData()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        viewModelScope.launch {
            repository.setTitle(title)
            repository.getManufacturers(manufacturers)
        }
    }
}
