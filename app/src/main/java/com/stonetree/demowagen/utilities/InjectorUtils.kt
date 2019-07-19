package com.stonetree.demowagen.utilities

import android.content.Context
import com.stonetree.demowagen.data.WagenDatabase
import com.stonetree.demowagen.features.builtdates.resources.repository.BuiltDatesRepository
import com.stonetree.demowagen.features.builtdates.viewmodel.BuiltDatesViewModelFactory
import com.stonetree.demowagen.features.cartypes.resources.repository.CarTypesRepository
import com.stonetree.demowagen.features.cartypes.viewmodel.CarTypesViewModelFactory
import com.stonetree.demowagen.data.WKDA
import com.stonetree.demowagen.features.manufacturer.model.ManufacturerViewModelFactory
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import com.stonetree.demowagen.features.productselection.resources.repository.ProductSelectionRepository
import com.stonetree.demowagen.features.productselection.viewmodel.ProductSelectionViewModelFactory

object InjectorUtils {

    fun provideManufacturerViewModelFactory(
        context: Context
    ): ManufacturerViewModelFactory {
        return ManufacturerViewModelFactory(ManufacturerRepository.getInstance(
            WagenDatabase.getInstance(context.applicationContext).wagenDao())
        )
    }

    fun provideCarTypesViewModelFactory(
        context: Context,
        wkda: WKDA
    ): CarTypesViewModelFactory {
        return CarTypesViewModelFactory(wkda, CarTypesRepository.getInstance(
            WagenDatabase.getInstance(context.applicationContext).wagenDao()))
    }

    fun provideBuiltDatesViewModelFactory(
        context: Context,
        carType: String
    ): BuiltDatesViewModelFactory {
        return BuiltDatesViewModelFactory(carType, BuiltDatesRepository.getInstance(
            WagenDatabase.getInstance(context.applicationContext).wagenDao()))
    }

    fun provideProductSelectionViewModelFactory(
        context: Context,
        builtDates: String
    ): ProductSelectionViewModelFactory {
        return ProductSelectionViewModelFactory(builtDates,
            ProductSelectionRepository.getInstance(
                WagenDatabase.getInstance(context.applicationContext).wagenDao()
            )
        )
    }
}