package com.example.clubdeportivovictoria.data.models

data class Cliente(
    val id: Int?,
    val nombre: String,
    val apellido: String,
    val dni: String,
    val telefono: String?,
    val email: String?,
    val fichaMedica: Int,
    val esSocio: Int,
    val proximaFechaPago: String?,
    val fechaInscripcion: String
)
