package com.lumen.valez_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class Location(
//    @PrimaryKey(autoGenerate = true)
    var locationId: Int = 0,
    var locationName: String,
    var locationType: String
)
