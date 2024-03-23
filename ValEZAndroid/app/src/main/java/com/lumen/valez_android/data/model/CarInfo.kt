package com.lumen.valez_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarInfo(
    val Count: Int,
    val Message: String,
    val Results: List<Result>,
    val SearchCriteria: String
) : Parcelable