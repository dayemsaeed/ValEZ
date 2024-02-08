package com.lumen.valez_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

//@Entity
data class Financial(
    //@PrimaryKey(autoGenerate = true)
    var financialId: Int = 0,
    var date: Date,
    var revenue: Number,
    var expenses: Number,
    var profit: Number,
    var period: String,
    var location: String
)
