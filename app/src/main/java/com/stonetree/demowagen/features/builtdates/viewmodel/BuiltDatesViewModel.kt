package com.stonetree.demowagen.features.builtdates.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.demowagen.features.builtdates.resources.repository.BuiltDatesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BuiltDatesViewModel(
    repository: BuiltDatesRepository
): ViewModel() {

    var builtDates: MutableLiveData<List<String>> = MutableLiveData()

    var title: MutableLiveData<String> = MutableLiveData()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        viewModelScope.launch {
            repository.setTitle(title)
            repository.saveCarType()
            repository.getBuiltDates(builtDates)
        }
    }
}