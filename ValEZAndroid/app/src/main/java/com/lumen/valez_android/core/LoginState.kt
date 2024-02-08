package com.lumen.valez_android.core

data class LoginState (
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val isError: String? = null
)