package com.aumarbello.showcase.data.models

import com.google.gson.annotations.SerializedName

data class CarDetailsResponse (
    val id: String,
    val year: Int,
    @SerializedName("insured")
    val isInsured: Boolean,
    val mileage: Long,
    val mileageUnit: String,
    @SerializedName("marketplacePrice")
    val price: Long,
    val imageUrl: String,
    val city: String,
    val state: String,
    val transmission: String,
    val fuelType: String,
    val sellingCondition: String,
    val createdAt: String,
    val hasFinancing: Boolean,
    val hasWarranty: Boolean,
    val exteriorColor: String,
    val engineType: String,
    val isFirstOwner: Boolean,
    val hasThreeDImage: Boolean,
    val threeDImageUrl: String?
)