package com.stonetree.demowagen.data.wkda

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository

class WKDADataSourceFactory(private val repository: ManufacturerRepository) : DataSource.Factory<Int, WKDA>() {

    val data = MutableLiveData<WKDADataSource>()

    private var source: WKDADataSource? = null

    override fun create(): DataSource<Int, WKDA> {
        source = WKDADataSource(repository)
        data.postValue(source)
        return source as WKDADataSource
    }
}
