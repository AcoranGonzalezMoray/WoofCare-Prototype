package com.example.woofcareapp.api.models

data class Advertisement(
    val id: Int,
    val objectId: Int,
    val type: Int,
    val name: String,
    val status: Int,
    val description: String,
    val publicationDate: String,
    val expirationDate: String
)
