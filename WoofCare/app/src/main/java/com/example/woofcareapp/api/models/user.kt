package com.example.woofcareapp.api.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val accountType: Int,
    val suscriptionType: Int,
    val location: String,
    val profileUrl: String,
    val phone: Long,
    val ratingId: Int,//nuevo
    val statusAccount: Int
)




fun accountTypeToString(accountType: Int): String {
    return when (accountType) {
        0 -> "Busca Servicios"
        1 -> "Oferta Servicios Cuidados"
        2 -> "Oferta Servicios Entrenamiento"
        else -> "Unknown Account Type"
    }
}
