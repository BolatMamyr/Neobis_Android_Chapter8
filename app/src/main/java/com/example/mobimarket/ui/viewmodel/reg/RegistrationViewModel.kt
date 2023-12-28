package com.example.mobimarket.ui.viewmodel.reg

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.models.api.ApiState
import com.example.mobimarket.models.reg.RegisterRequestBody
import com.example.mobimarket.models.reg.SignInRequestBody
import com.example.mobimarket.models.reg.SignInResponse
import com.example.mobimarket.repo.AuthRepository
import com.example.mobimarket.util.getErrorResult
import com.example.mobimarket.util.getSuccessResult
import com.example.mobimarket.util.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RegistrationViewModel"
@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _registrationState = MutableSharedFlow<ApiState<String>>()
    val registrationState = _registrationState.asSharedFlow()

    private val _signInState = MutableSharedFlow<ApiState<SignInResponse>>()
    val signInState = _signInState.asSharedFlow()

    fun register(body: RegisterRequestBody) {
        viewModelScope.launch {
            try {
                _registrationState.emit(ApiState.Loading)

                val response = authRepository.register(body)
                Log.d(TAG, "register: ${response.code()} ${response.message()}")
                if (response.isSuccessful) {
                    val data = response.body()?.string() ?: "Success"
                    _registrationState.emit(ApiState.Success(data))
                } else {
                    val errorResponse = response.getErrorResult()
                    _registrationState.emit(ApiState.Error(errorResponse))
                }
            } catch (e: Exception) {
                Log.e(TAG, "register: $e")
                _registrationState.emit(ApiState.Error(e.toErrorResponse()))
            }
        }
    }

    fun signIn(body: SignInRequestBody) {
        viewModelScope.launch {
            try {
                _signInState.emit(ApiState.Loading)

                val response = authRepository.signIn(body)
                Log.d(TAG, "signIn: ${response.code()} ${response.message()}")
                if (response.isSuccessful) {
                    val data = response.getSuccessResult<SignInResponse>()
                    _signInState.emit(ApiState.Success(data))
                } else {
                    val errorResponse = response.getErrorResult()
                    _signInState.emit(ApiState.Error(errorResponse))
                }
            } catch (e: Exception) {
                Log.e(TAG, "signIn: $e")
                _signInState.emit(ApiState.Error(e.toErrorResponse()))
            }
        }
    }
}