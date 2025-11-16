package com.example.clubdeportivovictoria.data.dao

import android.content.Context
import com.example.clubdeportivovictoria.data.db.DBHelper

object  ServiceLocator {
    private lateinit var db: DBHelper

    val clienteDao: ClienteDao by lazy { ClienteDao(db) }
    val cuotaDao: CuotaDao by lazy { CuotaDao(db) }
    val pagoActividadDao: PagoActividadDao by lazy { PagoActividadDao(db) }
    val actividadDao: ActividadDao by lazy { ActividadDao(db) }

    val userDao: UserDao by lazy { UserDao(db) }

    fun init(context: Context) {
        db = DBHelper(context.applicationContext)
    }
}