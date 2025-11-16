package com.example.clubdeportivovictoria.data.dao

import com.example.clubdeportivovictoria.data.db.DBHelper
import com.example.clubdeportivovictoria.data.models.Cuota

class CuotaDao(private val dbHelper: DBHelper) {

     fun pagar(cuota: Cuota, dni: String): Long {
        return dbHelper.insertarPago(cuota, dni);
    }
}