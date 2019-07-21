package com.stonetree.demowagen.features.cartypes.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.features.cartypes.model.CarTypesResponse
import com.stonetree.demowagen.features.cartypes.resources.api.CarTypesApi
import com.stonetree.demowagen.data.wagen.Wagen
import com.stonetree.demowagen.data.wagen.WagenDao
import com.stonetree.demowagen.data.wkda.WKDA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class CarTypesRepository private constructor(private val wagenDao: WagenDao) {

    private lateinit var wagen: Wagen
    private lateinit var wkda: WKDA

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadWagen()
        }
    }

    companion object {
        @Volatile
        private var instance: CarTypesRepository? = null
        fun getInstance(wagenDao: WagenDao) = instance ?: synchronized(this) {
            CarTypesRepository(wagenDao).also {
                instance = it
            }
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            wagenDao.updateManufacturerId("")
            wagenDao.updateManufacturerName("")
        }
    }

    suspend fun saveManufacturer(wkda: WKDA) {
        withContext(Dispatchers.IO) {
            if(wkda.id.isNotEmpty()) {
                instance?.wkda = wkda
                wagenDao.updateManufacturerId(wkda.id)
                wagenDao.updateManufacturerName(wkda.name)
            }
        }
    }

    suspend fun loadWagen() {
        withContext(Dispatchers.IO) {
            wagenDao.getWagen().apply {
                wagen = this
            }
        }
    }

    suspend fun setTitle(title: MutableLiveData<String>) {
        withContext(Dispatchers.IO) {
            wagen.apply {
                title.postValue(name.plus(" $carType").plus(" $builtDate"))
            }
        }
    }

    suspend fun getCarTypes(data: MutableLiveData<List<String>>) {
        val api = CoreRepository
            .getInstance()
            .retrofit
            .create(CarTypesApi::class.java)

        val request: Call<CarTypesResponse> = api.getCarTypes(id = wkda.id)
        withContext(Dispatchers.IO) {
            request.enqueue {
                onResponse = { response ->
                    parse(response, data)
                }

                onFailure = { error ->
                    Log.e(javaClass.name, error?.message)
                }
            }
        }
    }

    private fun parse(
        response: Response<CarTypesResponse>,
        data: MutableLiveData<List<String>>
    ) {
        response.body()?.wkda?.values?.let {
            data.postValue(it.toList())
        }
    }
}