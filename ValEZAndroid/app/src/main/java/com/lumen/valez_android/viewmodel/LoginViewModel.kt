package com.lumen.valez_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.valez_android.core.LoginState
import com.lumen.valez_android.core.Resource
import com.lumen.valez_android.data.repository.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {
    private var _loginState = MutableStateFlow(value = LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateUsername(input: String) {
        this.username = input
    }

    fun updatePassword(input: String) {
        this.password = input
    }

    fun login() = viewModelScope.launch {
        repository.login(username, password).collectLatest {result ->
            when(result) {
                is Resource.Loading -> {
                    _loginState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _loginState.update { it.copy(isSuccess = "Login Successful!") }
                    _navigationEvent.emit("main")
                }
                is Resource.Error -> {
                    _loginState.update { it.copy(isError = result.message) }
                    _navigationEvent.emit("error/${result.message}")
                }
            }
        }
    }
}