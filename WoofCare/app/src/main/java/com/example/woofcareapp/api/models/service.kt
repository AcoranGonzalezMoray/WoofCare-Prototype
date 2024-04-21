package com.example.woofcareapp.api.models

data class Service(
    val id: Int,
    val name: String,
    val type: Int,
    val status: Int,
    val publicationDate: String,
    val description: String,
    val price: Double,
    val uid: Int,
    val reviewId: Int,//nuevo
    val bannerUrl:List<String> //cambiar split , y hacer lista
)
