package com.aumarbello.showcase.repo

import com.aumarbello.showcase.data.api.ShowcaseService
import com.aumarbello.showcase.data.preferences.ShowcasePreferences
import com.aumarbello.showcase.utils.TestObjects
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class CarsRepositoryTest {
    private val service = Mockito.mock(ShowcaseService::class.java)
    private val preferences = Mockito.mock(ShowcasePreferences::class.java)

    private var repository = CarsRepository(preferences, service)
    private val carId = "007"

    @Before
    fun setUp() {
        runBlocking {
            `when`(service.loadCarDetails(carId)).thenReturn(TestObjects.carDetailsResponse)
            `when`(service.loadCarMedia(carId)).thenReturn(TestObjects.mediaResponse)
        }
    }

    @Test
    fun verifyOutputFormat() {
        runBlocking {
            val carDetails = repository.getCarDetails(carId)

            assertThat(carDetails.title, `is`("Honda Accord"))
            assertThat(carDetails.mileage, `is`("20000 km"))
            assertThat(carDetails.price, `is`("₦3,200,100"))
            assertThat(carDetails.datePosted, `is`("06 Jul 2021"))

            assertThat(carDetails.media, `is`(emptyList()))

            assertTrue(!carDetails.information.contains("Finance Options"))
        }
    }
}