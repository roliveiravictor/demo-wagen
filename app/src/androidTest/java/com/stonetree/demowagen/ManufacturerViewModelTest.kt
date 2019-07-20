package com.stonetree.demowagen

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.stonetree.demowagen.data.Wagen
import com.stonetree.demowagen.data.WagenDatabase
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import com.stonetree.demowagen.features.manufacturer.viewmodel.ManufacturerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.Assert.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1

class ManufacturerViewModelTest {

    private lateinit var wagenDatabase: WagenDatabase
    private lateinit var vm: ManufacturerViewModel

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        wagenDatabase = Room.inMemoryDatabaseBuilder(context, WagenDatabase::class.java).build()

        val dao = WagenDatabase.getInstance(context.applicationContext).wagenDao()
        val repository = ManufacturerRepository.getInstance(dao)
        vm = ManufacturerViewModel(repository)
    }

    @After
    fun finish() {
        runBlocking { vm.repository.createWagen() }
        wagenDatabase.close()
    }

    @Test
    fun test_defaultValues_shouldReturnNotNull() {
        assertNotNull(vm.manufacturers)
        assertNotNull(vm.title)
    }

    @Test
    fun test_loadWagen_shouldtReturnDefaultWagen() {
        runBlocking {
            vm.repository.loadWagen()

            val wagen = vm.repository
                .accessField("wagen")
                    as Wagen

            assertThat(wagen.manufacturerId, `is`(""))
            assertThat(wagen.name, `is`(""))
            assertThat(wagen.carType, `is`(""))
            assertThat(wagen.builtDate, `is`(""))
        }
    }
}