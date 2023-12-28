package com.example.mobimarket.repo

import com.example.mobimarket.api.Api
import com.example.mobimarket.models.product.AddProductRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: Api) {

    suspend fun addProduct(body: AddProductRequestBody): Response<ResponseBody> {
        return api.addProduct(body)
    }
}