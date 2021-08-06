package com.aumarbello.showcase.data.models

import com.google.gson.annotations.SerializedName

data class Car(
    val id: String,
    val title: String,
    val imageUrl: String,
    val year: Int,
    val city: String,
    val state: String,
    val sellingCondition: String,
    val hasWarranty: Boolean,
    @SerializedName("marketplacePrice")
    val marketPrice: Long,
    val hasFinancing: Boolean,
    val mileage: Long,
    val mileageUnit: String,
    val websiteUrl: String,
    @SerializedName("sold")
    val isSold: Boolean,
    val hasThreeDImage: Boolean,
    @SerializedName("gradeScore")
    val rating: Double?,
    val threeDImageUrl: String?
)
