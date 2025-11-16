package com.example.clubdeportivovictoria.data.models

data class PagoActividad(
    val id: Int? = 0,
    val clienteId: Int?,
    val fecha: String,          // YYYY-MM-DD
    val actividadId: Int
)
