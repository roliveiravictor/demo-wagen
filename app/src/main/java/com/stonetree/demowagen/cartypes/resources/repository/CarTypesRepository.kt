package com.stonetree.demowagen.cartypes.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.cartypes.resources.api.CarTypesApi
import com.stonetree.demowagen.manufacturer.model.ManufacturerResponse
import com.stonetree.demowagen.manufacturer.resources.api.ManufacturerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import stonetree.com.meals.core.provider.CoreRepository

class CarTypesRepository{

    companion object {
        @Volatile private var instance: CarTypesRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CarTypesRepository().also { instance = it }
            }
    }

    suspend fun getCarTypes(data: MutableLiveData<List<String>>) {
        val api = CoreRepository.retrofit.create(CarTypesApi::class.java)
        val request: Call<ManufacturerResponse> = api.getCarTypes()
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