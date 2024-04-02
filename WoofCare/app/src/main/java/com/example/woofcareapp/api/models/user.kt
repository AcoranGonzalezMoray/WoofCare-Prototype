package com.example.woofcareapp.api.models

data class User(
    var id: Int,
    var name: String,
    var email: String,
    var password: String,
    var accountType: Int, // 0: Busca Servicios, 1: Oferta Servicios Cuidados, 2: Oferta Servicios Entrenamiento
    var subscriptionType: String, // A, B, C, D
    var location: String,
    var profileUrl: String,
    var phone: String,
    var service: String
    )

fun accountTypeToString(accountType: Int): String {
    return when (accountType) {
        0 -> "Busca Servicios"
        1 -> "Oferta Servicios Cuidados"
        2 -> "Oferta Servicios Entrenamiento"
        else -> "Unknown Account Type"
    }
}
