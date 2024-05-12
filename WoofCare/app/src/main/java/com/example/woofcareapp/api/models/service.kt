package com.example.woofcareapp.api.models

data class Service(
    val id: Int,
    var name: String,
    var type: Int,
    var status: Int,
    var publicationDate: String,
    var description: String,
    var price: Double,
    var uid: Int,
    var bannerUrl:String //cambiar split , y hacer lista
)
