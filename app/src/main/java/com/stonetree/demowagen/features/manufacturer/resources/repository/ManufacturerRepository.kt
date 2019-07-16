package com.stonetree.demowagen.features.manufacturer.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.features.manufacturer.model.ManufacturerResponse
import com.stonetree.demowagen.features.manufacturer.model.WKDA
import com.stonetree.demowagen.features.manufacturer.resources.api.ManufacturerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class ManufacturerRepository {

    companion object {
        @Volatile private var instance: ManufacturerRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ManufacturerRepository().also { instance = it }
            }
    }

    suspend fun getManufacturers(data: MutableLiveData<List<WKDA>>) {
        val api = CoreRepository.retrofit.create(ManufacturerApi::class.java)
        val request: Call<ManufacturerResponse> = api.getManufacturers()
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
        response: Response<ManufacturerResponse>,
        data: MutableLiveData<List<WKDA>>
    ) {
        val wkdaList = arrayListOf<WKDA>()
        response.body()?.wkda?.forEach { wkda ->
            val row = WKDA(wkda.key, wkda.value)
            wkdaList.add(row)
        }
        data.postValue(wkdaList)
    }
}
