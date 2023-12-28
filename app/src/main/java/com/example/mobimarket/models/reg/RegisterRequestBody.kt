package com.example.mobimarket.models.reg

data class RegisterRequestBody(
    val username: String,
    val email: String,
    val password: String
)