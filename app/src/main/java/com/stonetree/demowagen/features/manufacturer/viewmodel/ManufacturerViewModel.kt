package com.stonetree.demowagen.features.manufacturer.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.stonetree.corerepository.CoreRepositoryConstant
import com.stonetree.corerepository.CoreRepositoryConstant.FETCH_DISTANCE
import com.stonetree.corerepository.CoreRepositoryConstant.PAGE_SIZE
import com.stonetree.demowagen.data.wkda.WKDA
import com.stonetree.demowagen.data.wkda.WKDADataSourceFactory
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import kotlinx.coroutines.*

class ManufacturerViewModel(val repository: ManufacturerRepository) : ViewModel() {

    private val factory: WKDADataSourceFactory = WKDADataSourceFactory(repository)

    private val config: PagedList.Config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(PAGE_SIZE)
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(FETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    var title: MutableLiveData<String> = MutableLiveData()

    val manufacturers: LiveData<PagedList<WKDA>> = LivePagedListBuilder(factory, config).build()

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
        }
    }
}
