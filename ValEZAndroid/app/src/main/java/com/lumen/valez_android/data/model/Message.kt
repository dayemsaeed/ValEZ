package com.lumen.valez_android.data.model

data class Message(
    val code: Int,
    val counter: Int,
    val credentials: String,
    val endpoint: String,
    val message: String,
    val version: String
)