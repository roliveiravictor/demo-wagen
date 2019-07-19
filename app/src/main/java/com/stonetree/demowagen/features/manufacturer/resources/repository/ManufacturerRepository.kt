package com.stonetree.demowagen.features.manufacturer.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDao
import com.stonetree.demowagen.features.manufacturer.model.ManufacturerResponse
import com.stonetree.demowagen.data.WKDA
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import com.stonetree.demowagen.features.manufacturer.resources.api.ManufacturerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class ManufacturerRepository private constructor(private val wagenDao: WagenDao){

    private var wagen: Wagen? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if(wagenDao.getWagen().isEmpty())
                createWagen()

            loadWagen()
        }
    }

    companion object {
        @Volatile private var instance: ManufacturerRepository? = null
        fun getInstance(wagenDao: WagenDao): ManufacturerRepository {
            instance?.apply { setup(this) }
            return instance ?: synchronized(this) {
                ManufacturerRepository(wagenDao).also {
                    instance = it
                }
            }
        }

        private fun setup(
            manufacturerRepository: ManufacturerRepository?
            ) {
            manufacturerRepository?.apply {
                CoroutineScope(Dispatchers.IO).launch {
                    loadWagen()
                }
            }
        }
    }

    suspend fun createWagen() {
        withContext(Dispatchers.IO) {
            wagenDao.insert(Wagen(-1, "", "", ""))
        }
    }

    suspend fun setTitle(title: MutableLiveData<String>) {
        withContext(Dispatchers.IO) {
            wagen?.apply {
                title.postValue(name.plus(" $carType").plus(" $builtDate"))
            }
        }
    }

    private fun loadWagen() {
        wagenDao.getWagen().apply {
            wagen = this.first()
        }
    }

    suspend fun getManufacturers(data: MutableLiveData<List<WKDA>>) {
        val api = CoreRepository
            .getInstance()
            .retrofit
            .create(ManufacturerApi::class.java)

        val request: Call<ManufacturerResponse> = api.getManufacturers()
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
        response: Response<ManufacturerResponse>,
        data: MutableLiveData<List<WKDA>>
    ) {
        val wkdaList = arrayListOf<WKDA>()
        response.body()?.wkda?.forEach { wkda ->
            val row = WKDA(wkda.key.toInt(), wkda.value)
            wkdaList.add(row)
        }
        data.postValue(wkdaList)
    }
}
