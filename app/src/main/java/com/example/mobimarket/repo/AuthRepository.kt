package com.example.mobimarket.repo

import com.example.mobimarket.api.Api
import com.example.mobimarket.models.reg.RegisterRequestBody
import com.example.mobimarket.models.reg.SignInRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: Api) {
    suspend fun register(body: RegisterRequestBody): Response<ResponseBody> {
        return api.register(body)
    }

    suspend fun signIn(body: SignInRequestBody): Response<ResponseBody> {
        return api.signIn(body)
    }
}