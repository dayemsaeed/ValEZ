package com.lumen.valez_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class Car(
    //@PrimaryKey(autoGenerate = true)
//    var carId: Int = 0,
//    var clientId: Int,
//    var vin: String,
//    var photoUrls: List<String>,
//    var make: String,
//    var color: String,
//    var type: String,
//    var comments: String
    val ticketId: Ticket,
    val imageUrl: String,
    val model: String,
    val color: String,
    val client: Client,
    val valetName: String,
    var status: CarStatus,
    val keysTurnedIn: Boolean
)

enum class CarStatus {
    PARKED, REQUESTED
}
