package com.lumen.valez_android.ui.view

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumen.valez_android.R
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme

@Composable
fun CarDetailsScreen() {
    ValEZAndroidTheme {
        Surface {
            CheckInForm()
        }
    }
}

@Composable
fun CheckInForm(modifier: Modifier = Modifier) {
    Column {
        Text(text = "Check In",
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(16.dp))
        ScanRows(fieldValue = "Ticket ID", onScanClicked = {})
        Spacer(modifier = Modifier.size(16.dp))
        ScanRows(fieldValue = "Full Name", onScanClicked = {})
        Spacer(modifier = Modifier.size(16.dp))
        ScanRows(fieldValue = "License Plate", onScanClicked = {})
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Make")
            Spacer(modifier = Modifier.size(64.dp))
            Text(text = "Type")
            Spacer(modifier = Modifier.size(64.dp))
            Text(text = "Color")
        }
        Text("Return Date")
        Button(
            modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            onClick = { /*TODO*/ }
        ) {
            Text("Next")
        }
    }
}

@Composable
fun ScanRows(modifier: Modifier = Modifier, fieldValue: String, onScanClicked: () -> Unit) {
    var value by remember { mutableStateOf(fieldValue) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(value = value,
            onValueChange = {
                value = it
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Image(painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier
                .size(57.dp)
                .clickable {
                    onScanClicked.invoke()
                }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CarDetailsScreenPreview(modifier: Modifier = Modifier) {
    CarDetailsScreen()
}