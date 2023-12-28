package com.example.mobimarket.models.api

data class ErrorResponse(
    val exceptionName: String,
    val httpStatus: String,
    val message: String
)