package com.stonetree.demowagen.manufacturer.resources.api

import com.stonetree.corerepository.CoreRepositoryConstant
import com.stonetree.demowagen.constants.Endpoint
import com.stonetree.demowagen.manufacturer.model.ManufacturerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ManufacturerApi {

    @GET(Endpoint.MANUFACTURER_PATH)
    fun getManufacturers(
        @Query(CoreRepositoryConstant.WA_KEY) key: String = CoreRepositoryConstant.WA_VALUE
    ): Call<ManufacturerResponse>

}
