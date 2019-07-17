package com.stonetree.demowagen.utilities

import com.stonetree.demowagen.features.builtdates.resources.repository.BuiltDatesRepository
import com.stonetree.demowagen.features.builtdates.viewmodel.BuiltDatesViewModelFactory
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import com.stonetree.demowagen.features.cartypes.viewmodel.CarTypesViewModelFactory
import com.stonetree.demowagen.features.manufacturer.model.WKDA

object InjectorUtils {

    fun provideCarTypesViewModelFactory(
        wkda: WKDA
    ): CarTypesViewModelFactory {
        return CarTypesViewModelFactory(wkda, CarTypesRepository.getInstance())
    }

    fun provideBuiltDatesViewModelFactory(
        carType: String
    ): BuiltDatesViewModelFactory {
        return BuiltDatesViewModelFactory(carType, BuiltDatesRepository.getInstance())
    }
}