package com.example.mobimarket.models.product

data class AddProductRequestBody(
    val name: String,
    val shortDescription: String,
    val fullDescription: String,
    val price: Int
)