package com.lumen.valez_android.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lumen.valez_android.ui.theme.ValEZAndroidTheme
import com.lumen.valez_android.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavHostController) {
    ValEZAndroidTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            RegisterForm(navController = navController)
        }
    }
}

@Composable
fun RegisterForm(viewModel: RegisterViewModel = hiltViewModel(), navController: NavHostController) {
    Column {
        OutlinedTextField(
            value = viewModel.username,
            onValueChange = {
                viewModel.updateUsername(it)
            },
            label = { Text(text = "User ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = {
                viewModel.updatePassword(it)
            },
            label = { Text(text = "PIN Code") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                viewModel.register()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Sign Up",
                modifier = Modifier.padding(0.dp, 8.dp),
                color = Color.Black
            )
        }
        LaunchedEffect(viewModel) {
            viewModel.navigationEvent.collect { route ->
                navController.navigate(route) {
                    // Pop up to the register screen to avoid back navigation to the registration form after successful registration or error
                    popUpTo("register") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun RegisterFormNoViewModel() {
    var email by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    Column {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text(text = "User ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = pin,
            onValueChange = {
                pin = it
            },
            label = { Text(text = "PIN Code") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Sign Up",
                modifier = Modifier.padding(0.dp, 8.dp),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RegisterPreview() {
    RegisterFormNoViewModel()
}