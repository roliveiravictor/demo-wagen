package com.stonetree.demowagen.features.builtdates.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.features.builtdates.model.BuiltDatesResponse
import com.stonetree.demowagen.features.builtdates.resources.api.BuiltDatesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class BuiltDatesRepository {

    companion object {
        @Volatile private var instance: BuiltDatesRepository? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: BuiltDatesRepository().also { instance = it }
            }
    }

    suspend fun getBuiltDates(date: String, data: MutableLiveData<List<String>>) {
        val api = CoreRepository.retrofit.create(BuiltDatesApi::class.java)
        val request: Call<BuiltDatesResponse> = api.getBuiltDates()
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