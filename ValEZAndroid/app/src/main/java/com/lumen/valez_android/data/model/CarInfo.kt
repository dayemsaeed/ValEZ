package com.lumen.valez_android.data.model

data class CarInfo(
    val Count: Int,
    val Message: String,
    val Results: List<Result>,
    val SearchCriteria: String
)