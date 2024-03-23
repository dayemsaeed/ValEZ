package com.lumen.valez_android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val Value: String,
    val ValueId: String,
    val Variable: String,
    val VariableId: Int
) : Parcelable