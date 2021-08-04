package com.aumarbello.showcase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aumarbello.showcase.data.models.CarDetails
import com.aumarbello.showcase.repo.CarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarDetailsVM @Inject constructor(
    private val repository: CarsRepository
): BaseVM() {
    private val _details = MutableLiveData<CarDetails>()
    val details: LiveData<CarDetails> = _details

    fun setCarId(id: String) {
        load { _details.value = repository.getCarDetails(id) }
    }
}