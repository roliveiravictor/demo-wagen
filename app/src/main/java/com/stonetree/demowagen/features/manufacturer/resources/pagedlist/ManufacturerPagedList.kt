package com.stonetree.demowagen.features.manufacturer.resources.pagedlist

import androidx.paging.PagedList
import com.stonetree.corerepository.CoreRepositoryConstant.PAGE_SIZE
import com.stonetree.corerepository.CoreRepositoryConstant.FETCH_DISTANCE
import com.stonetree.corerepository.CoreRepositoryConstant.INITIAL_KEY
import com.stonetree.demowagen.data.wkda.WKDADao
import com.stonetree.demowagen.data.wkda.WKDA
import com.stonetree.demowagen.features.manufacturer.resources.executor.ManufacturerExecutor

class ManufacturerPagedList private constructor(val wkdaDao: WKDADao){

    private val executor = ManufacturerExecutor()

    private val paginationConfig: PagedList.Config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(PAGE_SIZE)
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(FETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    companion object {
        @Volatile
        private var instance: ManufacturerPagedList? = null
            fun getInstance(wkdaDao: WKDADao) = instance ?: synchronized(this) {
                ManufacturerPagedList(wkdaDao).also {
                    instance = it
                }
        }
    }

    fun fetchPage(): PagedList<WKDA> = PagedList.Builder<Int, WKDA>(
            wkdaDao.getAll().create(),
            paginationConfig)
            .setInitialKey(INITIAL_KEY)
            .setFetchExecutor(executor)
            .setNotifyExecutor(executor)
            .build()
}