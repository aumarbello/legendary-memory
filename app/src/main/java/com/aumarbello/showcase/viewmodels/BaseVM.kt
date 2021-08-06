package com.aumarbello.showcase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseVM: ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    protected fun load(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _loader.value = true
                block()
            } catch (ex: Exception) {
                _error.value = ex.message
            } finally {
                _loader.value = false
            }
        }
    }
}