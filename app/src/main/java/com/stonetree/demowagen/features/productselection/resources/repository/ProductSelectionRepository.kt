package com.stonetree.demowagen.features.productselection.resources.repository

import androidx.lifecycle.MutableLiveData
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDao
import com.stonetree.demowagen.features.builtdates.resources.repository.BuiltDatesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private fun loadWagen() {
        wagenDao.getWagen().apply {
            wagen = this
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