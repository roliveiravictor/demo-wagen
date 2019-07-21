package com.stonetree.demowagen.features.manufacturer.resources.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stonetree.corerepository.enqueue
import com.stonetree.demowagen.data.wagen.Wagen
import com.stonetree.demowagen.data.wagen.WagenDao
import com.stonetree.demowagen.features.manufacturer.model.ManufacturerResponse
import com.stonetree.demowagen.data.wkda.WKDADao
import com.stonetree.demowagen.data.wkda.WKDA
import com.stonetree.demowagen.features.manufacturer.resources.api.ManufacturerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import stonetree.com.meals.core.provider.CoreRepository

class ManufacturerRepository
private constructor(private val wkdaDao: WKDADao,
                     private val wagenDao: WagenDao)
{
    private lateinit var wagen: Wagen

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if(wagenDao.getWagen() == null)
                createWagen()

            loadWagen()
        }
    }

    companion object {
        @Volatile
        private var instance: ManufacturerRepository? = null
        fun getInstance(wkdaDao: WKDADao, wagenDao: WagenDao) = instance ?: synchronized(this) {
            ManufacturerRepository(wkdaDao, wagenDao).also {
                instance = it
            }
        }
    }

    suspend fun createWagen() {
        withContext(Dispatchers.IO) {
            wagenDao.insert(Wagen("", "", "", ""))
        }
    }

    suspend fun setTitle(title: MutableLiveData<String>) {
        withContext(Dispatchers.IO) {
            wagen.apply {
                title.postValue(name.plus(" $carType").plus(" $builtDate"))
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

    suspend fun cacheApiData(hasManufacturers: MutableLiveData<Boolean>) {
        val api = CoreRepository
            .getInstance()
            .retrofit
            .create(ManufacturerApi::class.java)

        val request: Call<ManufacturerResponse> = api.getManufacturers()
        withContext(Dispatchers.IO) {
            request.enqueue {
                onResponse = { wkdas ->
                    CoroutineScope(Dispatchers.IO).launch {
                        wkdaDao.insertAll(toBeCached(hasManufacturers, wkdas))
                    }
                }

                onFailure = { error ->
                    Log.e(javaClass.name, error?.message)
                }
            }
        }
    }

    private fun toBeCached(hasManufacturers: MutableLiveData<Boolean>,
                           response: Response<ManufacturerResponse>) : List<WKDA>
    {
        val list = arrayListOf<WKDA>()
        response.body()?.wkda?.forEach { wkda ->
            val row = WKDA(wkda.value)
            row.id = wkda.key
            list.add(row)
        }

        if(list.isEmpty())
            hasManufacturers.postValue(false)
        else
            hasManufacturers.postValue(true)

        return list
    }

    fun getManufacturers() = wkdaDao.getAll()
}
