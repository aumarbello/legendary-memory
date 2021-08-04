package com.aumarbello.showcase.data.models

import com.google.gson.annotations.SerializedName

data class PaginatedResponse <T> (
    @SerializedName("makeList")
    val items: List<T>,
    val pagination: Pagination
)

data class Pagination(
    val total: Int,
    val currentPage: Int,
    val pageSize: Int
)
