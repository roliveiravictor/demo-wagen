package com.stonetree.demowagen.utilities

import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import com.stonetree.demowagen.features.cartypes.viewmodel.CarTypesViewModelFactory

object InjectorUtils {

    fun provideCarTypesViewModelFactory(
        manufacturerId: String
    ): CarTypesViewModelFactory {
        return CarTypesViewModelFactory(manufacturerId, CarTypesRepository.getInstance())
    }
}