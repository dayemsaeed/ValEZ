package com.lumen.valez_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

//@Entity
data class Ticket(
    //@PrimaryKey(autoGenerate = true)
    var ticketId: Int = 0,
    var carId: Int,
    var checkIn: Timestamp,
    var checkOut: Timestamp,
    var cost: Number,
    var employeeId: Int
)
