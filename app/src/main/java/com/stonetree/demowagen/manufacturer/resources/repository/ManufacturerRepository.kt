package com.stonetree.demowagen.manufacturer.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.manufacturer.model.ManufacturerResponse
import com.stonetree.demowagen.manufacturer.resources.api.ManufacturerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import stonetree.com.meals.core.provider.CoreRepository

class ManufacturerRepository {

    companion object {
        @Volatile private var instance: ManufacturerRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ManufacturerRepository().also { instance = it }
            }
    }

    suspend fun getManufacturers(data: MutableLiveData<List<String>>) {
        val api = CoreRepository.retrofit.create(ManufacturerApi::class.java)
        val request: Call<ManufacturerResponse> = api.getManufacturers()
        withContext(Dispatchers.IO) {
            request.enqueue {
                onResponse = { response ->
                    response.body()?.wkda?.values?.let {
                        data.postValue(it.toList())
                    }
                    Log.i(javaClass.name, response.body().toString())
                }

                onFailure = { error ->
                    Log.e(javaClass.name, error?.message)
                }
            }
        }
    }
}
