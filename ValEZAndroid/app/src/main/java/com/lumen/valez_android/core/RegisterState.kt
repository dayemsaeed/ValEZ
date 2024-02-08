package com.lumen.valez_android.core

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: String? = null,
    val isError: String? = null
)