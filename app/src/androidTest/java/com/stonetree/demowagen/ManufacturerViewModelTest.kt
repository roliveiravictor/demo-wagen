package com.stonetree.demowagen

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.stonetree.demowagen.data.wagen.Wagen
import com.stonetree.demowagen.data.wagen.WagenDatabase
import com.stonetree.demowagen.features.manufacturer.resources.repository.ManufacturerRepository
import com.stonetree.demowagen.features.manufacturer.viewmodel.ManufacturerViewModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.Assert.*

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