package com.example.clubdeportivovictoria.data.dao

import com.example.clubdeportivovictoria.data.db.DBHelper
import com.example.clubdeportivovictoria.data.models.Cuota
import com.example.clubdeportivovictoria.data.models.User

class UserDao(private val dbHelper: DBHelper) {

     fun buscarUsuarioPorNombre(nombre: String): User? {
        return dbHelper.buscarUsuarioPorNombre(nombre);
    }
}