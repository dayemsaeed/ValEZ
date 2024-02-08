package com.lumen.valez_android.data.repository

import com.google.firebase.auth.AuthResult
import com.lumen.valez_android.core.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Resource<AuthResult>>
    fun register(email: String, password: String): Flow<Resource<AuthResult>>
}