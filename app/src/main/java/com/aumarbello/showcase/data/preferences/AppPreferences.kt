package com.aumarbello.showcase.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferences (
    private val context: Context
): ShowcasePreferences {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "showcase")

    override val cartInfo: Flow<Int>
        get() = context.dataStore.data.map { it[CART_COUNT] ?: 0 }

    override suspend fun addToCart() {
        context.dataStore.edit { preferences ->
            val currentValue = preferences[CART_COUNT] ?: 0
            preferences[CART_COUNT] = currentValue.inc()
        }
    }

    override suspend fun clearCart() {
        context.dataStore.edit { preferences ->
            preferences[CART_COUNT] = 0
        }
    }

    private companion object {
        val CART_COUNT = intPreferencesKey("cart counter")
    }
}