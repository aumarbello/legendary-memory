package com.aumarbello.showcase.data.models

data class CarDetails (
    val id: String,
    val year: Int,
    val mileage: String,
    val price: String,
    val imageUrl: String,
    val location: String,
    val datePosted: String,
    val hasThreeDImage: Boolean,
    val threeDImageUrl: String?,
    val information: List<String>,
    val media: List<CarMedia>
)