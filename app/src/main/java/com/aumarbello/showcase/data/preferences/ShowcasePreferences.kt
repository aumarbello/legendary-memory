package com.aumarbello.showcase.data.preferences

import kotlinx.coroutines.flow.Flow

interface ShowcasePreferences {
    val cartInfo: Flow<Int>

    suspend fun addToCart()
    suspend fun clearCart()
}