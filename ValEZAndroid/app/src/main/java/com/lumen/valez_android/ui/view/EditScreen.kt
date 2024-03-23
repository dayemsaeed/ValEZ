package com.lumen.valez_android.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lumen.valez_android.R
import com.lumen.valez_android.data.model.Car
import com.lumen.valez_android.data.model.CarStatus
import com.lumen.valez_android.data.model.Client
import com.lumen.valez_android.data.model.Ticket
import com.lumen.valez_android.ui.theme.Cyan80
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme
import com.lumen.valez_android.ui.theme.White
import com.lumen.valez_android.viewmodel.CarViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EditScreen(ticketId: String? = "", navController: NavController, viewModel: CarViewModel) {
    ValEZAndroidTheme {
        Surface {
            EditCarDetails(ticketId, onCheckInComplete = { navController.popBackStack() }, viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun EditCarDetails(ticketId: String?, onCheckInComplete: () -> Unit, viewModel: CarViewModel, navController: NavController) {
    // When the composable enters the composition, select the car with the given ticketId
    LaunchedEffect(ticketId) {
        ticketId?.let { viewModel.selectCarByTicketId(it) }
    }

    // Observe the selectedCar LiveData
    val car by viewModel.selectedCar.observeAsState()
    val state = rememberScrollState()

    var name by remember { mutableStateOf("") }
    name = car!!.client.name
    var phone by remember { mutableStateOf("") }
    phone = car!!.client.phoneNumber
    var color by remember { mutableStateOf("") }
    color = car!!.color



    // Use a Scaffold for the layout to have a top bar and potentially a bottom bar
    Scaffold(
        topBar = {
            car?.let {car ->
                Text(
                    text = "Ticket ID: ${car.ticketId.ticketId}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            }
        },
        modifier = Modifier.padding(8.dp)
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .verticalScroll(state)
                    .padding(4.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Display the details for the selected car
                car?.let { car ->
                    val checkInFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(car.ticketId.checkIn)
                    // Add other Text composables for each detail
                    Text(text = "Full Name") // Replace with actual data
                    TextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Phone Number") // Replace with actual data
                    TextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Vehicle")
                    TextField(
                        value = car.model,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Color") // Replace with actual data
                    TextField(
                        value = color,
                        onValueChange = {
                            color = it
                        },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Park Time") // Replace with actual data
                    TextField(
                        value = checkInFormat,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    /*Text(text = "Arrived") // Replace with actual data
                    TextField(
                        value = car.valetName,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))*/
                    Text(text = "Payment") // Replace with actual data
                    TextField(
                        value = "Pending",
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Amount") // Replace with actual data
                    TextField(
                        value = "$30",
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Pickup") // Replace with actual data
                    TextField(
                        value = "11:30 AM",
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Airline") // Replace with actual data
                    TextField(
                        value = "Air Blue",
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Parked By") // Replace with actual data
                    TextField(
                        value = car.valetName,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(onClick = {
                        // When the Next button is clicked, create a Car object with the entered details
                        // and pass it back via the onCheckInComplete callback
                        val newCar = Car(
                            ticketId = Ticket(
                                ticketId = ticketId!!.toInt(),
                                carId = car.ticketId.carId,
                                checkIn = car.ticketId.checkIn,
                                checkOut = null,
                                cost = 30,
                                employeeId = 1
                            ),
                            client = Client(
                                name = name,
                                phoneNumber = phone,
                                email = car.client.email
                            ),
                            color = color,
                            imageUrl = car.imageUrl, // Assign a default or a placeholder image URL
                            model = car.model,
                            //waitTime = formattedTime, // You might want to calculate or set a default wait time
                            valetName = car.valetName, // Use full name for valet name, if that's correct
                            status = car.status, // Default to 'Parked' upon check-in
                            keysTurnedIn = car.keysTurnedIn // Change as needed; maybe add a switch for this
                        )
                        viewModel.editCar(newCar)
                        onCheckInComplete()
                    },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = White)
                    ) {
                        Text(
                            text = "Next",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                } ?: run {
                    // Show some loading indicator or placeholder
                    Text("Loading car details...")
                }
            }
        }
    }
}