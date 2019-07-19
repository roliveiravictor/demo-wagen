package com.stonetree.demowagen.features.builtdates.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.data.WKDA
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDao
import com.stonetree.demowagen.features.builtdates.model.BuiltDatesResponse
import com.stonetree.demowagen.features.builtdates.resources.api.BuiltDatesApi
import com.stonetree.demowagen.features.cartypes.model.CarTypesResponse
import com.stonetree.demowagen.features.cartypes.resources.api.CarTypesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class BuiltDatesRepository private constructor(private val wagenDao: WagenDao) {

    private lateinit var wagen: Wagen
    private lateinit var carType: String

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadWagen()
        }
    }

    companion object {
        @Volatile
        private var instance: BuiltDatesRepository? = null
        fun getInstance(wagenDao: WagenDao) = instance ?: synchronized(this) {
            BuiltDatesRepository(wagenDao).also {
                instance = it
            }
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            wagenDao.updateCarType("")
        }
    }

    suspend fun saveCarType(carType: String) {
        withContext(Dispatchers.IO) {
            if(carType.isNotEmpty()) {
                instance?.carType = carType
                wagenDao.updateCarType(carType)
            }
            loadWagen()
        }
    }

    suspend fun setTitle(title: MutableLiveData<String>) {
        withContext(Dispatchers.IO) {
            wagen.apply {
                title.postValue(name.plus(" $carType").plus(" $builtDate"))
            }
        }
    }

    private fun loadWagen() {
        wagenDao.getWagen().apply {
            wagen = this
        }
    }

    suspend fun getBuiltDates(data: MutableLiveData<List<String>>) {
        val api = CoreRepository.
            getInstance().
            retrofit.
            create(BuiltDatesApi::class.java)

        val request: Call<BuiltDatesResponse> = api.getBuiltDates(id = wagen?.manufacturerId, type = carType)
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
        response: Response<BuiltDatesResponse>,
        data: MutableLiveData<List<String>>
    ) {
        response.body()?.wkda?.values?.let {
            data.postValue(it.toList())
        }
    }
}