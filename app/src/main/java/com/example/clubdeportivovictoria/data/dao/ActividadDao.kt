package com.example.clubdeportivovictoria.data.dao

import com.example.clubdeportivovictoria.data.db.DBHelper
import com.example.clubdeportivovictoria.data.models.Actividad

class ActividadDao(private val dbHelper: DBHelper) {


    fun listarTodos():  MutableList<Actividad>? {
        return dbHelper.buscaractividades();
    }

    fun buscarActividadPorNombre(actividad : String):  Actividad? {
        return dbHelper.buscarActividadPorNombre(actividad);
    }
}