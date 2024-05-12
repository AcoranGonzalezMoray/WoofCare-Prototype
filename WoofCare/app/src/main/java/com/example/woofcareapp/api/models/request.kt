package com.example.woofcareapp.api.models

data class Request(
    val id: Int,
    val uidReceiver: String,
    val uidSender: String,
    val serviceId: Int,
    var status: String,
    val creationDate: String
)
