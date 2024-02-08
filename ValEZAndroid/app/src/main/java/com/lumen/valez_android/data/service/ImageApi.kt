package com.lumen.valez_android.data.service

import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("/image")
    suspend fun getCarImage(@Query("vin") vin: String)
}