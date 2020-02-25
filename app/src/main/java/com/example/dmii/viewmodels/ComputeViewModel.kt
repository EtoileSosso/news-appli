package com.example.dmii.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dmii.fragments.Operation

class ComputeViewModel: ViewModel() {
    private val _resultLiveData = MutableLiveData<Double>()
    val resultLiveData: LiveData<Double>
        get() = _resultLiveData

    fun compute(value1: Double, value2: Double, operation: Operation) {
        _resultLiveData.value = when (operation) {
            Operation.SUM -> value1.plus(value2)
            Operation.DIVISION -> value1.div(value2)
            Operation.PRODUCT -> value1.times(value2)
            Operation.MINUS -> value1.minus(value2)
        }
    }
}