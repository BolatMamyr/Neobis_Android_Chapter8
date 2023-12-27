package com.example.mobimarket.repo

import com.example.mobimarket.api.Api
import com.example.mobimarket.models.RegisterRequestBody
import com.example.mobimarket.models.RegisterRequestResult
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: Api) {
    fun register(body: RegisterRequestBody): Response<RegisterRequestResult> {
        return api.register(body)
    }
}