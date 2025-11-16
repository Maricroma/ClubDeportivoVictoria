package com.example.clubdeportivovictoria.data.dao

import com.example.clubdeportivovictoria.data.db.DBHelper
import com.example.clubdeportivovictoria.data.models.Cliente

class ClienteDao(private val dbHelper: DBHelper) {

    fun insertarCliente(cliente: Cliente): Long {
        return dbHelper.insertarCliente(cliente.nombre,
            cliente.apellido, cliente.dni,
            cliente.telefono,cliente.email,
            cliente.fichaMedica,
            cliente.esSocio,
            cliente.proximaFechaPago,
            cliente.fechaInscripcion);
    }

    fun buscarPorDni(dni: String): Cliente? {
       return dbHelper.buscarClientePorDni(dni);
    }

    fun actualizarCliente(cliente: Cliente):  Int {
        return dbHelper.actualizarCliente(cliente);
    }

    fun clientesMorosos():  MutableList<Cliente>? {
        return dbHelper.buscarClientePagoAVencer();
    }

}