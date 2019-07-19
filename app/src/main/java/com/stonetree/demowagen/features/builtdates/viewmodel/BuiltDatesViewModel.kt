package com.stonetree.demowagen.features.builtdates.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.features.builtdates.resources.repository.BuiltDatesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BuiltDatesViewModel(val repository: BuiltDatesRepository): ViewModel() {

    var builtDates: MutableLiveData<List<String>> = MutableLiveData()

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
            repository.saveCarType()
            repository.setTitle(title)
            repository.getBuiltDates(builtDates)
        }
    }
}