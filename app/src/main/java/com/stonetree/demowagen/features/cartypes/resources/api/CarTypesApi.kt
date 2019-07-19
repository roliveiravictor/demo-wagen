package com.stonetree.demowagen.features.cartypes.resources.api

import com.stonetree.corerepository.CoreRepositoryConstant
import com.stonetree.corerepository.CoreRepositoryConstant.WA_KEY
import com.stonetree.demowagen.features.cartypes.model.CarTypesResponse
import com.stonetree.demowagen.constants.Endpoint
import com.stonetree.demowagen.features.cartypes.resources.constants.CarTypeRepositoryConstants.MANUFACTURER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CarTypesApi {

    @GET(Endpoint.CAR_TYPES_PATH)
    fun getCarTypes(
        @Query(WA_KEY) key: String = CoreRepositoryConstant.WA_VALUE,
        @Query(MANUFACTURER) id: String
    ): Call<CarTypesResponse>

}