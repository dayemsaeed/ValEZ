package com.lumen.valez_android.data.service

import com.lumen.valez_android.data.model.CarInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CarsApi {
    @GET("vehicles/DecodeVinExtended/{vin}")
    suspend fun getCarInfo(@Path("vin") vin: String, @Query("format") format: String): Response<CarInfo>
}