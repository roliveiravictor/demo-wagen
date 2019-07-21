package com.stonetree.demowagen.features.manufacturer.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.stonetree.demowagen.data.wkda.WKDA
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ManufacturerViewModel(val repository: ManufacturerRepository) : ViewModel() {

    var manufacturers: MutableLiveData<PagedList<WKDA>> = MutableLiveData()

    var title: MutableLiveData<String> = MutableLiveData()

    fun getManufacturers() = manufacturers.value

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        viewModelScope.launch {
            repository.createWagen()
            repository.loadWagen()

            repository.setTitle(title)

            repository.cacheApiData()

            repository.getManufacturers(manufacturers)
        }
    }
}
