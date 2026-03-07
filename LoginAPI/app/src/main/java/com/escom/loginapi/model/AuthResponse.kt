package com.escom.loginapi.model

data class AuthResponse(
    val message: String,
    val username: String? = null
)