package com.example.mobimarket.ui.logged_in.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.models.api.ApiState
import com.example.mobimarket.models.product.AddProductRequestBody
import com.example.mobimarket.repo.ProductRepository
import com.example.mobimarket.util.getErrorResult
import com.example.mobimarket.util.toErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AddProductViewModel"

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _addProductState = MutableSharedFlow<ApiState<String>>()
    val addProductState = _addProductState.asSharedFlow()

    fun addProduct(body: AddProductRequestBody) {
        viewModelScope.launch {
            _addProductState.emit(ApiState.Loading)
            try {
                val response = productRepository.addProduct(body)
                Log.d(TAG, "addProduct: ${response.code()} ${response.message()}")
                if (response.isSuccessful) {
                    val data = response.body()?.string() ?: "Success"
                    _addProductState.emit(ApiState.Success(data))
                } else {
                    _addProductState.emit(ApiState.Error(response.getErrorResult()))
                }
            } catch (e: Exception) {
                Log.d(TAG, "addProduct: $e")
                _addProductState.emit(ApiState.Error(e.toErrorResponse()))
            }
        }
    }


}