package com.example.clubdeportivovictoria.data.dao

import com.example.clubdeportivovictoria.data.db.DBHelper
import com.example.clubdeportivovictoria.data.models.PagoActividad

class PagoActividadDao(private val dbHelper: DBHelper) {

    fun pagarCuota(pagoActividad: PagoActividad): Long {
        return dbHelper.insertarPagoActividad(pagoActividad);
    }

}