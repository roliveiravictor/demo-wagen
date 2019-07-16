package com.stonetree.demowagen.features.cartypes.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.features.cartypes.model.CarTypesResponse
import com.stonetree.demowagen.features.cartypes.resources.api.CarTypesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class CarTypesRepository {

    companion object {
        @Volatile private var instance: CarTypesRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CarTypesRepository().also { instance = it }
            }
    }

    suspend fun getCarTypes(manufacturerId: String, data: MutableLiveData<List<String>>) {
        val api = CoreRepository.retrofit.create(CarTypesApi::class.java)
        val request: Call<CarTypesResponse> = api.getCarTypes(id = manufacturerId)
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
        response: Response<CarTypesResponse>,
        data: MutableLiveData<List<String>>
    ) {
        response.body()?.wkda?.values?.let {
            data.postValue(it.toList())
        }
    }
}