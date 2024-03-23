package com.lumen.valez_android.data.model

import java.util.Date

//@Entity
data class Ticket(
    //@PrimaryKey(autoGenerate = true)
    var ticketId: Int = 0,
    var carId: Int,
    var checkIn: Date,
    var checkOut: Date?,
    var cost: Number,
    var employeeId: Int
)
