package com.example.mobimarket.models

data class ProductMini(
    val id: Int,
    val name: String,
    val price: Int,
    val images: List<ProductImage>,
    val likes: Int
)
