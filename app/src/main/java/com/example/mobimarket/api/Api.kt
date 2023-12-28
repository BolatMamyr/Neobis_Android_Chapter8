package com.example.mobimarket.api

import com.example.mobimarket.models.reg.RegisterRequestBody
import com.example.mobimarket.models.reg.SignInRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("api/v1/auth/registration")
    suspend fun register(
        @Body body: RegisterRequestBody
    ): Response<ResponseBody>

    @POST("api/v1/auth/login")
    suspend fun signIn(
        @Body body: SignInRequestBody
    ): Response<ResponseBody>
}