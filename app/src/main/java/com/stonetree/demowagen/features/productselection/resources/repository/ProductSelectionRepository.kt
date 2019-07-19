package com.stonetree.demowagen.features.productselection.resources.repository

import androidx.lifecycle.MutableLiveData
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductSelectionRepository private constructor(private var builtDates: String, private val wagenDao: WagenDao){

    private var wagen: Wagen? = null

    companion object {
        @Volatile private var instance: ProductSelectionRepository? = null
        fun getInstance(builtDates: String, wagenDao: WagenDao) =
            instance.apply { setup(this, builtDates) } ?: synchronized(this) {
                instance ?: ProductSelectionRepository(builtDates, wagenDao).also {
                    instance = it
                }
            }

        private fun setup(
            carTypesRepository: ProductSelectionRepository?,
            builtDates: String
        ) {
            carTypesRepository?.apply {
                this.builtDates = builtDates
                CoroutineScope(Dispatchers.IO).launch {
                    loadWagen()
                }
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
            instance?.wagen = this.first()
        }
    }

    suspend fun saveBuiltDate() {
        withContext(Dispatchers.IO) {
            wagenDao.updateBuiltDate(builtDates)
        }
    }

    suspend fun setWagen(wagen: MutableLiveData<Wagen>) {
        withContext(Dispatchers.IO) {
            instance?.wagen?.apply {
                wagen.postValue(this)
            }
        }
    }
}