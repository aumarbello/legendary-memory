package com.aumarbello.showcase.repo

import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aumarbello.showcase.data.api.ShowcaseService
import com.aumarbello.showcase.data.models.Brand
import com.aumarbello.showcase.data.models.Car
import com.aumarbello.showcase.data.models.CarDetails
import com.aumarbello.showcase.data.paging.GenericPagingSource
import com.aumarbello.showcase.data.preferences.ShowcasePreferences
import com.aumarbello.showcase.utils.formatDate
import com.aumarbello.showcase.utils.formatPrice
import com.aumarbello.showcase.utils.toInitialCap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CarsRepository @Inject constructor(
    private val preferences: ShowcasePreferences,
    private val service: ShowcaseService
) {
     fun getCarBrands(): Pager<Int, Brand> {
        return Pager(PagingConfig(20)) {
            GenericPagingSource(Unit) { page, _ ->
                service.loadBrands(page)
            }
        }
    }

    fun searchCars(query: String): Pager<Int, Car> {
        return Pager(PagingConfig(20)) {
            GenericPagingSource(query) { page, query ->
                service.searchVehicles(query, page)
            }
        }
    }

    suspend fun getCarDetails(id: String) = withContext(Dispatchers.IO) {
        val carDetails = service.loadCarDetails(id)

        val information = mutableListOf(
            carDetails.sellingCondition,
            carDetails.exteriorColor,
            carDetails.transmission,
            carDetails.year.toString(),
            carDetails.engineType,
            carDetails.fuelType,
            carDetails.model.wheelType
        )
        if (carDetails.hasWarranty) information.add("Warranty")
        if (carDetails.hasFinancing) information.add("Finance Options")
        if (carDetails.isInsured) information.add("Insured")
        if (carDetails.isFirstOwner) information.add("First Owner")


        CarDetails(
            carDetails.id,
            "${carDetails.model.make.name} ${carDetails.model.name}",
            carDetails.year,
            "${carDetails.mileage} ${carDetails.mileageUnit}",
            carDetails.price.formatPrice(),
            carDetails.imageUrl,
            "${carDetails.city}, ${carDetails.state}",
            carDetails.createdAt.formatDate(),
            carDetails.hasThreeDImage,
            carDetails.threeDImageUrl,
            information.map { it.toInitialCap() },
            service.loadCarMedia(id).mediaList
        )
    }

    suspend fun addToCart() = withContext(Dispatchers.IO) {
        preferences.addToCart()
    }

    suspend fun clearCart() = withContext(Dispatchers.IO) {
        preferences.clearCart()
    }

    fun getCartInfo() = preferences.cartInfo.asLiveData()
}