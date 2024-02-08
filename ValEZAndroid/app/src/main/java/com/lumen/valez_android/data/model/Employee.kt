package com.lumen.valez_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

//@Entity
data class Employee(
    //@PrimaryKey(autoGenerate = true)
    var employeeId: Int = 0,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var hireDate: Date,
    var username: String,
    var location: String
)
