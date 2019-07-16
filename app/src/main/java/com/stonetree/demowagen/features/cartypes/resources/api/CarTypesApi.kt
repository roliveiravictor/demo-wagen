package com.stonetree.demowagen.features.cartypes.resources.api

import com.stonetree.corerepository.CoreRepositoryConstant
import com.stonetree.demowagen.features.cartypes.model.CarTypesResponse
import com.stonetree.demowagen.constants.DirectionsBundleKey
import com.stonetree.demowagen.constants.Endpoint
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CarTypesApi {

    @GET(Endpoint.CAR_TYPES_PATH)
    fun getCarTypes(
        @Query(CoreRepositoryConstant.WA_KEY) key: String = CoreRepositoryConstant.WA_VALUE,
        @Query(DirectionsBundleKey.MANUFACTURER) manufacturer: String = "107"
    ): Call<CarTypesResponse>

}