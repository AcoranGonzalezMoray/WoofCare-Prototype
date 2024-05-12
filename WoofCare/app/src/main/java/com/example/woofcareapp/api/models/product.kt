package com.example.woofcareapp.api.models

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val location: String,
    val companyName: String,
    val reviewId: Int,//nuevo
    val webUrl: String,//nuevo
    val status: Int,
    val bannerUrls: String //cambiar split , y hacer lista
)
