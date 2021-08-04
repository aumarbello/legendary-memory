package com.aumarbello.showcase.data.api

import com.aumarbello.showcase.data.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowcaseService {
    @GET("inventory/make?popular=true")
    suspend fun loadBrands(@Query("page") page: Int): PaginatedResponse<Brand>

    @GET("inventory/car/search")
    suspend fun searchVehicles(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): PaginatedResponse<Car>

    @GET("inventory/car/{carId}")
    suspend fun loadCarDetails(@Path("carId") id: String): CarDetailsResponse

     @GET("inventory/car_media")
     suspend fun loadCarMedia(@Query("carId") id: String): CarMediaResponse
}