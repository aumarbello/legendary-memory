package com.aumarbello.showcase.data.models

import com.google.gson.annotations.SerializedName

data class CarMediaResponse (
    @SerializedName("carMediaList")
    val mediaList: List<CarMedia>
)

data class CarMedia(
    val id: Long,
    val name: String,
    val url: String,
    val type: String
)