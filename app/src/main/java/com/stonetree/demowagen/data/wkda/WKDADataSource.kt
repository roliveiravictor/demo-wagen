package com.stonetree.demowagen.data.wkda

import androidx.paging.PositionalDataSource
import com.stonetree.corerepository.CoreRepositoryConstant.PAGE_SIZE
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WKDADataSource(private val repository: ManufacturerRepository) : PositionalDataSource<WKDA>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<WKDA>) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getManufacturers()?.apply {
                if(this.isNotEmpty()) {
                    val position = computeInitialLoadPosition(params, 0)
                    val loadSize = computeInitialLoadSize(params, position, PAGE_SIZE)
                    val sublist = subList(position, position + loadSize)
                    callback.onResult(sublist, position, size)
                }
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<WKDA>) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getManufacturers()?.apply {
                callback.onResult(
                    subList(
                        params.startPosition,
                        params.startPosition + params.loadSize
                    )
                )
            }
        }
    }
}