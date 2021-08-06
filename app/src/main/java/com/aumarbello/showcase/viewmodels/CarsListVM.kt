package com.aumarbello.showcase.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aumarbello.showcase.repo.CarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarsListVM @Inject constructor(
    private val repository: CarsRepository
): BaseVM() {
    fun getCarBrands() = repository.getCarBrands()
        .flow
        .cachedIn(viewModelScope)

    fun searchCars(query: String) = repository.searchCars(query)
        .flow
        .cachedIn(viewModelScope)

    fun getCartInfo() = repository.getCartInfo()

    fun addToCart() {
        load { repository.addToCart() }
    }
}