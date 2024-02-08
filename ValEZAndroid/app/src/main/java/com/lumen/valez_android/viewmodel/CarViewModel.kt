package com.lumen.valez_android.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.valez_android.data.model.CarInfo
import com.lumen.valez_android.data.service.CarsApi
import com.lumen.valez_android.data.service.ImageApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    val carApi: CarsApi,
    val imageApi: ImageApi
) : ViewModel() {
    private val _carInfo = MutableLiveData<CarInfo?>()
    val carInfo: LiveData<CarInfo?> = _carInfo

    fun fetchCarInfo(vin: String, modelYear: String) {
        viewModelScope.launch {
            try {
                val response = carApi.getCarInfo(vin, "json", modelYear)
                if (response.isSuccessful) {
                    _carInfo.value = response.body()
                }
                else {
                    _carInfo.value = null
                    Log.e("CarViewModel", "Error fetching car info: ${response.errorBody()?.string()}")
                }
            }
            catch (e: Exception) {
                _carInfo.value = null
                Log.e("CarViewModel", "Exception fetching car info", e)
            }
        }
    }
}