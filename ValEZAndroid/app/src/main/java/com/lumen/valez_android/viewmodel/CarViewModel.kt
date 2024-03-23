package com.lumen.valez_android.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.valez_android.data.model.Car
import com.lumen.valez_android.data.model.CarInfo
import com.lumen.valez_android.data.model.CarStatus
import com.lumen.valez_android.data.model.Client
import com.lumen.valez_android.data.model.ImageResult
import com.lumen.valez_android.data.model.Ticket
import com.lumen.valez_android.data.service.CarsApi
import com.lumen.valez_android.data.service.ImageApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CarViewModel @Inject constructor(
    private val carApi: CarsApi,
    private val imageApi: ImageApi
) : ViewModel() {

    // Form fields
    var ticketNumber = mutableStateOf("")
    var phoneNumber = mutableStateOf("")
    var fullName = mutableStateOf("")
    var licenseVin = mutableStateOf("")
    var make = mutableStateOf("")
    var model = mutableStateOf("")
    var color = mutableStateOf("")
    var returnDate = mutableStateOf("")
    var imgUrl = ""

    var carList = mutableStateListOf<Car>()

    // LiveData to hold the details of the selected car
    private val _selectedCar = MutableLiveData<Car?>()
    val selectedCar: MutableLiveData<Car?> = _selectedCar

    private val _carDetails = MutableLiveData<CarInfo?>()
    val carDetails: MutableLiveData<CarInfo?> = _carDetails

    private val _imageDetails = MutableLiveData<ImageResult?>()
    val imageDetails: MutableLiveData<ImageResult?> = _imageDetails

    // Function to select a car by its ticketId
    fun selectCarByTicketId(ticketId: String) {
        // Find the car in the dummy list with the matching ticketId
        // This will be replaced by an API call or database query in a real app
        val foundCar = carList.find { it.ticketId.ticketId.toString() == ticketId }
        Log.d("CarViewModel", "Found car for ticket ID $ticketId: $foundCar")
        _selectedCar.value = foundCar
    }

    // Function to generate a pair of random timestamps with a max difference of 5 hours
    private fun generateCheckInCheckOutTimestamps(): Pair<java.util.Date, java.util.Date> {
        val now = LocalDateTime.now().minusDays(Random.nextLong(0, 1)) // Random day up to 5 days ago
        val startOfDay = now.withHour(1).withMinute(0).withSecond(0) // Start of that day
        val randomMinuteOfDay = Random.nextLong(0, 24 * 60) // Random minute of the day
        val checkInDateTime = startOfDay.plusMinutes(randomMinuteOfDay)
        val checkOutDateTime = checkInDateTime.plusHours(Random.nextLong(1, 6)) // 1 to 5 hours later

        val checkInTimestamp = Timestamp.from(checkInDateTime.atZone(ZoneId.systemDefault()).toInstant())
        val checkOutTimestamp = Timestamp.from(checkOutDateTime.atZone(ZoneId.systemDefault()).toInstant())

        return Pair(checkInTimestamp, checkOutTimestamp)
    }

    init {
        // Populate with dummy data
        carList = mutableStateListOf(
            Car(
                ticketId = Ticket(ticketId = 1, carId = 1, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 10, employeeId = 1),
                imageUrl = "https://i.pinimg.com/originals/4d/de/fb/4ddefbcd5ac9b94ea7533bb9f8ae194f.png",
                model = "Q3 Sportback",
                color = "Silver",
                client = Client(name = "Connor Lowe", phoneNumber = "123-456-7890", email = "clowe@example.com"),
                //waitTime = "35 Min",
                valetName = "Alex",
                status = CarStatus.PARKED,
                keysTurnedIn = true
            ),
            Car(
                ticketId = Ticket(ticketId = 2, carId = 2, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 15, employeeId = 2),
                imageUrl = "https://assets-global.website-files.com/60ce1b7dd21cd517bb39ff20/61a7aef223e62f330b9203a7_tesla-model-x.png",
                model = "Tesla Model X",
                color = "Black",
                client = Client(name = "John Doe", phoneNumber = "987-654-3210", email = "johnd@example.com"),
                //waitTime = "45 Min",
                valetName = "Johnny",
                status = CarStatus.REQUESTED,
                keysTurnedIn = false
            ),
            Car(
                ticketId = Ticket(ticketId = 3, carId = 3, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 20, employeeId = 3),
                imageUrl = "https://pngimg.com/d/mustang_PNG29.png",
                model = "Ford Mustang",
                color = "Red",
                client = Client(name = "Bradley Martin", phoneNumber = "456-123-7890", email = "brad@example.com"),
                //waitTime = "35 Min",
                valetName = "Benny",
                status = CarStatus.PARKED,
                keysTurnedIn = true
            ),
            Car(
                ticketId = Ticket(ticketId = 4, carId = 4, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 12, employeeId = 4),
                imageUrl = "https://pngimg.com/d/camaro_PNG36.png",
                model = "Chevrolet Camaro",
                color = "Yellow",
                client = Client(name = "Sandy Bruschi", phoneNumber = "321-654-9870", email = "sandy@example.com"),
                //waitTime = "15 Min",
                valetName = "Sam",
                status = CarStatus.PARKED,
                keysTurnedIn = false
            ),
            Car(
                ticketId = Ticket(ticketId = 5, carId = 5, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 18, employeeId = 5),
                imageUrl = "https://www.pngmart.com/files/22/Porsche-911-PNG-Picture.png",
                model = "Porsche 911",
                color = "Blue",
                client = Client(name = "Lando Norris", phoneNumber = "789-456-1230", email = "lando@example.com"),
                //waitTime = "25 Min",
                valetName = "Luis",
                status = CarStatus.REQUESTED,
                keysTurnedIn = true
            ),
            Car(
                ticketId = Ticket(ticketId = 6, carId = 6, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 25, employeeId = 6),
                imageUrl = "https://i.pinimg.com/originals/fa/4d/2c/fa4d2c29bbc89d64407d1682adfbbe19.png",
                model = "Lamborghini Huracan",
                color = "Green",
                client = Client(name = "Diana Smith", phoneNumber = "123-456-7891", email = "diana@example.com"),
                //waitTime = "55 Min",
                valetName = "Sarah",
                status = CarStatus.PARKED,
                keysTurnedIn = true
            ),
            Car(
                ticketId = Ticket(ticketId = 7, carId = 7, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 8, employeeId = 7),
                imageUrl = "https://vehicle-images.dealerinspire.com/stock-images/thumbnails/large/chrome/e31c0afefff8b2de10d054f1ea100b0f.png",
                model = "Toyota Corolla",
                color = "White",
                client = Client(name = "Lucille Ball", phoneNumber = "987-654-3211", email = "lucy@example.com"),
                //waitTime = "10 Min",
                valetName = "Matt",
                status = CarStatus.REQUESTED,
                keysTurnedIn = false
            ),
            Car(
                ticketId = Ticket(ticketId = 8, carId = 8, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 12, employeeId = 8),
                imageUrl = "https://freebiescloud.com/wp-content/uploads/2020/10/1-2.png",
                model = "Honda Civic",
                color = "Black",
                client = Client(name = "Bruno Mars", phoneNumber = "456-123-7891", email = "bruno@example.com"),
                //waitTime = "20 Min",
                valetName = "Olivia",
                status = CarStatus.PARKED,
                keysTurnedIn = false
            ),
            Car(
                ticketId = Ticket(ticketId = 9, carId = 9, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 30, employeeId = 9),
                imageUrl = "https://vehicle-images.dealerinspire.com/stock-images/chrome/8dabb5b608373e2a025a54a0305d11cc.png",
                model = "Ford F-150",
                color = "Blue",
                client = Client(name = "Max Verstappen", phoneNumber = "789-456-1231", email = "max@example.com"),
                //waitTime = "45 Min",
                valetName = "Daniel",
                status = CarStatus.REQUESTED,
                keysTurnedIn = true
            ),
            Car(
                ticketId = Ticket(ticketId = 10, carId = 10, checkIn = generateCheckInCheckOutTimestamps().first, checkOut = generateCheckInCheckOutTimestamps().second, cost = 20, employeeId = 10),
                imageUrl = "https://pngimg.com/d/dodge_PNG89.png",
                model = "Dodge Charger",
                color = "Red",
                client = Client(name = "Peter Griffin", phoneNumber = "321-654-9871", email = "peter@example.com"),
                //waitTime = "60 Min",
                valetName = "Grace",
                status = CarStatus.PARKED,
                keysTurnedIn = true
            )
        )
    }

    // Function to fetch car information by VIN
    fun fetchCarInfo(vin: String) {
        viewModelScope.launch {
            try {
                val response = carApi.getCarInfo(vin, "json")
                if (response.isSuccessful) {
                    val carInfo = response.body()
                    // Assuming carInfo contains the details you need, like make and model
                    _carDetails.postValue(carInfo) // Update a LiveData or state variable with these details
                    licenseVin.value = vin
                    carInfo.let {
                        make.value = it?.Results?.get(7)?.Value.toString()
                        model.value = it?.Results?.get(12)?.Value.toString()
                        licenseVin.value = vin
                    }

                } else {
                    Log.e("CarViewModel", "Error fetching car details: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CarViewModel", "Exception fetching car details", e)
            }
        }

        viewModelScope.launch {
            try {
                val response = imageApi.getCarImage(vin = vin)
                if (response.isSuccessful) {
                    val imageInfo = response.body()
                    imageDetails.postValue(imageInfo)
                    imageInfo.let {
                        imgUrl = it?.data?.image.toString()
                    }
                } else {
                    Log.e("CarViewModel", "Error fetching car image: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CarViewModel", "Exception fetching car image", e)
            }
        }
    }

    fun addCar(car: Car) {
        // Get the current list of cars and create a new list with the new car added
        carList.add(car)
        carList.sortBy { currentCar ->
            currentCar.ticketId.ticketId
        }
    }

    fun editCar(car: Car) {
        carList.remove(selectedCar.value)
        addCar(car)
    }

}
