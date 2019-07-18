package com.stonetree.demowagen.features.cartypes.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDao
import com.stonetree.demowagen.features.cartypes.model.CarTypesResponse
import com.stonetree.demowagen.features.cartypes.resources.api.CarTypesApi
import com.stonetree.demowagen.data.WKDA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class CarTypesRepository private constructor(private var wkda: WKDA, private val wagenDao: WagenDao){

    private var wagen: Wagen? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadWagen()
        }
    }

    companion object {
        @Volatile private var instance: CarTypesRepository? = null
        fun getInstance(wkda: WKDA, wagenDao: WagenDao) =
            instance.apply { this?.wkda = wkda } ?: synchronized(this) {
                instance ?: CarTypesRepository(wkda, wagenDao).also {
                    instance = it
                }
            }
    }

    suspend fun saveManufacturer() {
        withContext(Dispatchers.IO) {
            wagenDao.updateManufacturerId(wkda.id)
            wagenDao.updateManufacturerName(wkda.name)
        }
    }

    suspend fun setTitle(title: MutableLiveData<String>) {
        withContext(Dispatchers.IO) {
            instance?.wagen?.apply {
                title.postValue(name.plus(" $carType").plus(" $builtDate"))
            }
        }
    }

    private suspend fun loadWagen() {
        withContext(Dispatchers.IO) {
            wagenDao.getWagen().value?.apply {
                instance?.wagen = this
            }
        }
    }

    suspend fun getCarTypes(data: MutableLiveData<List<String>>) {
        val api = CoreRepository.
            getInstance().
            retrofit.
            create(CarTypesApi::class.java)

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