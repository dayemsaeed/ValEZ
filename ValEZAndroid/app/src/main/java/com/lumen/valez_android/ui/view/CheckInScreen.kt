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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CheckInScreen(navController: NavController, carViewModel: CarViewModel) {
    ValEZAndroidTheme {
        Surface {
            CheckInForm({
                navController.popBackStack()
            }, carViewModel, navController)
        }
    }
}

@Composable
fun CheckInForm(
    onCheckInComplete: () -> Unit,
    viewModel: CarViewModel,
    navController: NavController
) {
    // State for each input field
    var ticketNumber by viewModel.ticketNumber
    var phoneNumber by viewModel.phoneNumber
    var fullName by viewModel.fullName
    var licenseVin by viewModel.licenseVin
    var make by viewModel.make
    var model by viewModel.model
    var color by viewModel.color
    var returnDate by viewModel.returnDate
    val currentTime = Instant.now()
    val formatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(ZoneId.systemDefault())
    val formattedTime = formatter.format(currentTime)

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(IntrinsicSize.Max)
                    ) {
                        OutlinedTextField(
                            leadingIcon = {
                                Icon(painter = painterResource(id = R.drawable.weight), contentDescription = "", tint = Cyan80)
                            },
                            value = ticketNumber,
                            onValueChange = { ticketNumber = it },
                            label = { Text("Ticket Number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(4f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                navController.navigate("ticketScan")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Cyan80),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.scan),
                                contentDescription = "Ticket Scan Icon",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.phone), contentDescription = "", tint = Cyan80)
                        },
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(IntrinsicSize.Max)
                    ) {
                        OutlinedTextField(
                            leadingIcon = {
                                Icon(painter = painterResource(id = R.drawable.user_icon_form), contentDescription = "", tint = Cyan80)
                            },
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("Full Name") },
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(IntrinsicSize.Max)
                    ) {
                        OutlinedTextField(
                            leadingIcon = {
                                Icon(painter = painterResource(id = R.drawable.id_card), contentDescription = "", tint = Cyan80)
                            },
                            value = licenseVin,
                            onValueChange = { licenseVin = it },
                            label = { Text("VIN") },
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                            modifier = Modifier.weight(4f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                navController.navigate("vinScan")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Cyan80),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.scan),
                                contentDescription = "VIN Scan Icon",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = make,
                        onValueChange = {
                            make = it
                        },
                        label = { Text("Make") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = model,
                        onValueChange = { model = it },
                        label = { Text("Model") },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = color,
                        onValueChange = { color = it },
                        label = { Text("Color") },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.calendar), contentDescription = "", tint = Cyan80)
                        },
                        value = returnDate,
                        onValueChange = { returnDate = it },
                        label = { Text("Return Date") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Button(onClick = {
                    // When the Next button is clicked, create a Car object with the entered details
                    // and pass it back via the onCheckInComplete callback
                    val newCar = Car(
                        ticketId = Ticket(
                            ticketId = ticketNumber.toInt(),
                            carId = 15,
                            checkIn = Timestamp.from(Instant.now()),
                            checkOut = null,
                            cost = 0,
                            employeeId = 1
                        ),
                        client = Client(
                            name = fullName,
                            phoneNumber = phoneNumber,
                            email = ""
                        ),
                        color = color,
                        imageUrl = viewModel.imgUrl, // Assign a default or a placeholder image URL
                        model = "$make $model",
                        //waitTime = formattedTime, // You might want to calculate or set a default wait time
                        valetName = fullName, // Use full name for valet name, if that's correct
                        status = CarStatus.PARKED, // Default to 'Parked' upon check-in
                        keysTurnedIn = true // Change as needed; maybe add a switch for this
                    )
                    viewModel.addCar(newCar)
                    ticketNumber = ""
                    fullName = ""
                    licenseVin = ""
                    phoneNumber = ""
                    make = ""
                    model = ""
                    color = ""
                    returnDate = ""
                    viewModel.imgUrl = ""
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
            }
        }
    }
}