package com.stonetree.demowagen.features.builtdates.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDao
import com.stonetree.demowagen.features.builtdates.model.BuiltDatesResponse
import com.stonetree.demowagen.features.builtdates.resources.api.BuiltDatesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class BuiltDatesRepository private constructor(val carType: String, val wagenDao: WagenDao) {

    private var wagen: Wagen? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadWagen()
        }
    }

    companion object {
        @Volatile private var instance: BuiltDatesRepository? = null
        fun getInstance(carType: String, wagenDao: WagenDao) =
            instance ?: synchronized(this) {
                instance ?: BuiltDatesRepository(carType, wagenDao).also {
                    instance = it
                }
            }
    }

    suspend fun saveCarType() {
        withContext(Dispatchers.IO) {
            wagenDao.updateCarType(carType)
        }
    }

    suspend fun setTitle(title: MutableLiveData<String>) {
        withContext(Dispatchers.IO) {
            wagen?.apply {
                title.postValue(name.plus(" $carType").plus(" $builtDate"))
            }
        }
    }

    private suspend fun loadWagen() {
        withContext(Dispatchers.IO) {
            wagenDao.getWagen().value?.apply {
                wagen = this
            }
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
                    Log.i(javaClass.name, response.body().toString())
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