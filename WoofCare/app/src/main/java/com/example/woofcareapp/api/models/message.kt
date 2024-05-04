package com.example.woofcareapp.api.models

data class Message(
    val id: Int,
    val uidReceiver: Int,
    val uidSender: Int,
    val type: String,
    val message: String,
    val sentDate: String,
    val serviceId: Int
)
