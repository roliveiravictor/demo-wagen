package com.stonetree.demowagen.features.productselection.resources.repository

import androidx.lifecycle.MutableLiveData
import com.stonetree.demowagen.data.wagen.Wagen
import com.stonetree.demowagen.data.wagen.WagenDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductSelectionRepository private constructor(private val wagenDao: WagenDao){

    private lateinit var wagen: Wagen

    companion object {
        @Volatile
        private var instance: ProductSelectionRepository? = null
        fun getInstance(wagenDao: WagenDao) = instance ?: synchronized(this) {
            ProductSelectionRepository(wagenDao).also {
                instance = it
            }
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            wagenDao.updateBuiltDate("")
        }
    }

    suspend fun loadWagen() {
        withContext(Dispatchers.IO) {
            wagenDao.getWagen().apply {
                wagen = this
            }
        }
    }

    suspend fun saveBuiltDate(builtDates: String) {
        withContext(Dispatchers.IO) {
            wagenDao.updateBuiltDate(builtDates)
        }
    }

    suspend fun setWagen(wagen: MutableLiveData<Wagen>) {
        withContext(Dispatchers.IO) {
            loadWagen()
            instance?.wagen?.apply {
                wagen.postValue(this)
            }
        }
    }
}