package com.stonetree.demowagen.features.builtdates.resources.api

import com.stonetree.corerepository.CoreRepositoryConstant
import com.stonetree.demowagen.constants.Endpoint.BUILT_DATES_PATH
import com.stonetree.demowagen.features.builtdates.model.BuiltDatesResponse
import com.stonetree.demowagen.features.builtdates.resources.constants.BuiltDatesRepositoryConstants.CAR_TYPE
import com.stonetree.demowagen.features.builtdates.resources.constants.BuiltDatesRepositoryConstants.MANUFACTURER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BuiltDatesApi {

    @GET(BUILT_DATES_PATH)
    fun getBuiltDates(
        @Query(CoreRepositoryConstant.WA_KEY) key: String = CoreRepositoryConstant.WA_VALUE,
        @Query(MANUFACTURER) id: String?,
        @Query(CAR_TYPE) type: String?
    ): Call<BuiltDatesResponse>

}