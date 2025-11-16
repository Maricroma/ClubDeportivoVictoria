package com.example.clubdeportivovictoria.data.models

data class Cuota(
    val id: Int? = 0,
    val clienteId: Int?,
    val fecha: String,           // YYYY-MM-DD
    val precio: Double,
    val formaPago: Boolean        // "Efectivo", "Tarjeta", etc.
)
