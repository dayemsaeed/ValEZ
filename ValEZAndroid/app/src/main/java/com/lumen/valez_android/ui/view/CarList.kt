package com.lumen.valez_android.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.lumen.valez_android.R
import com.lumen.valez_android.data.model.Car
import com.lumen.valez_android.data.model.CarStatus
import com.lumen.valez_android.ui.theme.Cyan80
import com.lumen.valez_android.ui.theme.Green80
import com.lumen.valez_android.ui.theme.Red80
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme
import com.lumen.valez_android.viewmodel.CarViewModel
import java.time.Instant
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.xml.datatype.DatatypeConstants.HOURS

@Composable
fun CarList(navController: NavHostController, viewModel: CarViewModel) {
    // State to hold the current filter ('Parked' or 'Requested')
    var filterStatus by remember { mutableStateOf(CarStatus.PARKED) }
    val carList = viewModel.carList

    ValEZAndroidTheme {
        Surface {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate("checkIn") },
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Car"
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End
            ) { innerPadding -> // This is the PaddingValues object
                Column(modifier = Modifier.padding(innerPadding)) { // Apply the padding here
                    // Filter buttons
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterButton("Parked", filterStatus == CarStatus.PARKED) {
                            filterStatus = CarStatus.PARKED
                        }
                        FilterButton("Requested", filterStatus == CarStatus.REQUESTED) {
                            filterStatus = CarStatus.REQUESTED
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    FilterRow(carList = carList)
                    Spacer(modifier = Modifier.height(8.dp))
                    // List of cars filtered by the selected status
                    ParkedList(
                        cars = carList.filter { it.status == filterStatus },
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun FilterRow(carList: List<Car>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Total: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            text = carList.size.toString(),
            color = Cyan80,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(text = "In: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            text = carList.count {
                it.status == CarStatus.PARKED
            }.toString(),
            color = Cyan80,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(text = "Out: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(
            text = carList.count {
                it.status == CarStatus.REQUESTED
            }.toString(),
            color = Cyan80,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, color = if (isSelected) Cyan80 else Color.LightGray),
        shape = RoundedCornerShape(8.dp)
        //colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) Color.Blue else Color.Gray)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            @DrawableRes val icon = if (text == "Parked") R.drawable.parked_icon else R.drawable.requested_icon
            Image(
                painter = painterResource(id = icon),
                contentDescription = "$text icon image",
                modifier = Modifier.size(64.dp)
            )
            Text(text = text, color = Color.White)
        }
    }
}

@Composable
fun CarCell(car: Car, onClick: () -> Unit) {
    val backgroundColor = if (car.keysTurnedIn) Green80 else Red80
    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = car.imageUrl,
                contentDescription = "${car.model} Image",
                // Assume you have placeholder and error drawables
                //placeholder = painterResource(id = R.drawable.placeholder),
                //error = painterResource(id = R.drawable.error),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(180.dp, 102.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = car.model, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "Clock Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = ChronoUnit.HOURS.between(car.ticketId.checkIn.toInstant(),
                        car.ticketId.checkOut?.toInstant() ?: Instant.now()
                    ).toString() + " hours")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.user_alt),
                        contentDescription = "Valet Person Icon"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = car.valetName)
                }
                // Include other details you want to show
            }
        }
    }
}

@Composable
fun ParkedList(cars: List<Car>, navController: NavHostController) {
    LazyColumn {
        items(cars) { car ->
            CarCell(car = car) {
                navController.navigate("car_detail/${car.ticketId.ticketId}")
            }
        }
    }
}