package com.example.mobimarket.models.api

sealed class ApiState<out T> {

    data object Loading : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error(val error: ErrorResponse) : ApiState<Nothing>()

}
