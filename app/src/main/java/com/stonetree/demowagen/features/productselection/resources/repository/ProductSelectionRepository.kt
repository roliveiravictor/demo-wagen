package com.stonetree.demowagen.features.productselection.resources.repository

import androidx.lifecycle.MutableLiveData
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductSelectionRepository private constructor(private val wagenDao: WagenDao){

    private var wagen: Wagen? = null

    companion object {
        @Volatile private var instance: ProductSelectionRepository? = null
        fun getInstance(wagenDao: WagenDao) =
            instance.apply { setup(this) } ?: synchronized(this) {
                instance ?: ProductSelectionRepository(wagenDao).also {
                    instance = it
                }
            }

        private fun setup(
            carTypesRepository: ProductSelectionRepository?
        ) {
            carTypesRepository?.apply {
                CoroutineScope(Dispatchers.IO).launch {
                    loadWagen()
                }
            }
        }
    }

    suspend fun setWagen(wagen: MutableLiveData<Wagen>) {
        withContext(Dispatchers.IO) {
            instance?.wagen?.apply {
                wagen.postValue(this)
            }
        }
    }

    private fun loadWagen() {
        wagenDao.getWagen().apply {
            instance?.wagen = this
        }
    }
}