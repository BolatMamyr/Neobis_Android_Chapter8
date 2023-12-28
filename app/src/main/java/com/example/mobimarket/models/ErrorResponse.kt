package com.example.mobimarket.models

data class ErrorResponse(
    val exceptionName: String,
    val httpStatus: String,
    val message: String
)