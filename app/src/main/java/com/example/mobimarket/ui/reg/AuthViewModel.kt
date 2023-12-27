package com.example.mobimarket.ui.reg

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mobimarket.models.RegisterRequestBody
import com.example.mobimarket.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun register(body: RegisterRequestBody) {
        val response = authRepository.register(body)
        Log.d("MyLog", "${response.code()}, ${response.message()}")
        Log.d("MyLog", "${response.body()?.token}")
        if (response.isSuccessful) {

        } else {

        }
    }
}