package com.example.mobimarket.models

data class RegisterRequestBody(
    val email: String,
    val password: String,
    val username: String
)