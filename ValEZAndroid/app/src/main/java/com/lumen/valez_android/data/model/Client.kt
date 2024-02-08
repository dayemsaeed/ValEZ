package com.lumen.valez_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class Client(
    //@PrimaryKey(autoGenerate = true)
    var clientId: Int = 0,
    var name: String,
    var phoneNumber: String,
    var email: String
)
