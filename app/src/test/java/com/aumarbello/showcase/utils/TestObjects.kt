package com.aumarbello.showcase.utils

import com.aumarbello.showcase.data.models.CarDetailsResponse
import com.aumarbello.showcase.data.models.CarMediaResponse
import com.aumarbello.showcase.data.models.CarModel
import com.aumarbello.showcase.data.models.ModelMake

object TestObjects {
    private val model = CarModel(
        10, "2WD", "Accord", ModelMake(100, "Honda")
    )

    val carDetailsResponse = CarDetailsResponse(
        "101",
        2009,
        true,
        20000,
        "km",
        3200100,
        "www.google.com",
        "Bariga",
        "Lagos",
        "automatic",
        "dl",
        "new",
        "2021-07-06T14:17:09.578Z",
        hasFinancing = false,
        hasWarranty = true,
        exteriorColor = "Black",
        engineType = "Fuel",
        isFirstOwner = false,
        hasThreeDImage = false,
        threeDImageUrl = null,
        model = model
    )

    val mediaResponse = CarMediaResponse(emptyList())
}