package com.stonetree.demowagen.cartypes.resources.api

import com.stonetree.corerepository.CoreRepositoryConstant
import com.stonetree.demowagen.constants.Endpoint
import com.stonetree.demowagen.manufacturer.model.ManufacturerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CarTypesApi {

    @GET(Endpoint.CAR_TYPES_PATH)
    fun getCarTypes(
        @Query(CoreRepositoryConstant.WA_KEY) key: String = CoreRepositoryConstant.WA_VALUE
    ): Call<ManufacturerResponse>

}