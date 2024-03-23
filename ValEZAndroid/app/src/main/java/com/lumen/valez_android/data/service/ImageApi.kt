package com.lumen.valez_android.data.service

import com.lumen.valez_android.data.model.ImageResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageApi {
    @GET("image")
    suspend fun getCarImage(@Header("authorization") auth: String = "Basic ZTViMTRjZmItZTQxYS00MzY0LTkxMmQtOGQyYjVjMmFiM2Y4", @Header("partner-token") token: String = "606861771229401c8c75af80d144d62c", @Query("vin") vin: String): Response<ImageResult>
}
