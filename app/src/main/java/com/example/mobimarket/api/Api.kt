package com.example.mobimarket.api

import com.example.mobimarket.models.RegisterRequestBody
import com.example.mobimarket.models.RegisterRequestResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("auth/registration")
    fun register(
        @Body body: RegisterRequestBody
    ): Response<RegisterRequestResult>
}