package com.lumen.valez_android.ui.view

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.lumen.valez_android.R
import com.lumen.valez_android.data.model.CarStatus
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme
import com.lumen.valez_android.viewmodel.CarViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

@Composable
fun CarDetailsScreen(ticketId: String?, navController: NavController, carViewModel: CarViewModel) {
    ValEZAndroidTheme {
        Surface {
            CarDetails(ticketId, carViewModel, navController)
        }
    }
}

@Composable
fun CarDetails(ticketId: String?, viewModel: CarViewModel, navController: NavController) {
    // When the composable enters the composition, select the car with the given ticketId
    LaunchedEffect(ticketId) {
        ticketId?.let { viewModel.selectCarByTicketId(it) }
    }

    // Observe the selectedCar LiveData
    val car by viewModel.selectedCar.observeAsState()
    val state = rememberScrollState()


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
        bottomBar = { CarDetailBottomBar(
            onEdit = {
                     navController.navigate("edit/${car!!.ticketId.ticketId}")
            },
            onText = { /*TODO*/ },
            onCheckout = {
                viewModel.selectedCar.value!!.ticketId.checkOut = Timestamp.from(Instant.now())
                viewModel.selectedCar.value!!.status = if (car!!.status == CarStatus.PARKED) CarStatus.REQUESTED else CarStatus.PARKED
                navController.popBackStack()
            },
            onPay = {/*TODO*/})
        },
        modifier = Modifier.padding(8.dp)
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .verticalScroll(state)
                    .padding(4.dp)
            ) {
                // Display the details for the selected car
                car?.let { car ->
                    val checkInFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(car.ticketId.checkIn)
                    // Add other Text composables for each detail
                    Text(text = "Full Name") // Replace with actual data
                    TextField(
                        value = car.client.name,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Phone Number") // Replace with actual data
                    TextField(
                        value = car.client.phoneNumber,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
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
                        value = car.color,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
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
                    // ... Add more details
                } ?: run {
                    // Show some loading indicator or placeholder
                    Text("Loading car details...")
                }
            }
        }
    }
}

@Composable
fun CarDetailBottomBar(onEdit: () -> Unit, onText: () -> Unit, onCheckout: () -> Unit, onPay: () -> Unit) {
    BottomAppBar { // This is a placeholder, replace with your actual UI components
        Button(onClick = onEdit, modifier = Modifier.weight(1f)) { Text("Edit") }
        Spacer(modifier = Modifier.padding(2.dp))
        Button(onClick = onText, modifier = Modifier.weight(1f)) { Text("Text") }
        Spacer(modifier = Modifier.padding(2.dp))
        Button(onClick = onCheckout, modifier = Modifier.weight(1.5f)) { Text("Checkout") }
        Spacer(modifier = Modifier.padding(2.dp))
        Button(onClick = onPay, modifier = Modifier.weight(1f)) { Text("Pay") }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CarDetailsScreenPreview(modifier: Modifier = Modifier) {
    val carViewModel: CarViewModel = hiltViewModel()
    //CarDetails("01", carViewModel, navController = Nav)
}