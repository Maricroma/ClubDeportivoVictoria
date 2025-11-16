package com.example.clubdeportivovictoria.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.clubdeportivovictoria.data.models.Actividad
import com.example.clubdeportivovictoria.data.models.Cliente
import com.example.clubdeportivovictoria.data.models.Cuota
import com.example.clubdeportivovictoria.data.models.PagoActividad
import com.example.clubdeportivovictoria.data.models.User
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ClienteMock(
    val nombre: String,
    val apellido: String,
    val dni: String,
    val telefono: String,
    val email: String
)

class DBHelper(context: Context) : SQLiteOpenHelper(context, "ClubVictoria.db", null, 6) {
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE usuarios( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT UNIQUE, " +
                    "pass TEXT, " +
                    "activo INTEGER DEFAULT 1 " +
        ");"
        );

        db.execSQL(
            "INSERT INTO usuarios(nombre, pass) VALUES " +
                    "('admin','123456');"
        );

        db.execSQL(
            "CREATE TABLE clientes(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "dni TEXT UNIQUE NOT NULL, " +
                    "apellido TEXT NOT NULL, " +
                    "telefono TEXT, " +
                    "email TEXT UNIQUE, " +
                    "ficha_medica INTEGER, " +
                    "es_socio INTEGER, " +
                    "proxima_fecha_pago TEXT, " +
                    "fecha_inscripcion TEXT NOT NULL" +
                    ");"
        )


        db.execSQL(
            "CREATE TABLE cuotas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cliente_id INTEGER NOT NULL, " +
                    "fecha TEXT NOT NULL, " +
        "precio REAL NOT NULL, " +
        "forma_pago TEXT NOT NULL, " +
                "FOREIGN KEY (cliente_id) REFERENCES clientes(id) " +
                "ON DELETE CASCADE" +
                ");"
        );

        db.execSQL(
            "CREATE TABLE actividades (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "precio REAL NOT NULL " +
        ");"
        );

        db.execSQL(
            "INSERT INTO actividades(nombre, precio) VALUES " +
                    "('Fútbol', 3500.00), " +
                    "('Básquet', 3200.00), " +
                    "('Tenis', 4000.00), " +
                    "('Natación', 4500.00), " +
                    "('Remo', 4600.00), " +
                    "('Gimnasio', 3000.00), " +
                    "('Yoga', 2800.00), " +
                    "('Pilates', 2900.00), " +
                    "('Zumba', 2700.00), " +
                    "('Vóley', 3100.00);"
        );

        db.execSQL(
            "CREATE TABLE pagos_actividades (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cliente_id INTEGER NOT NULL, " +
                    "fecha TEXT NOT NULL, " +
        "actividad_id INTEGER NOT NULL, " +
                "FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (actividad_id) REFERENCES actividades(id) ON DELETE CASCADE " +
                ");"
        );

        insertarClientesMockCuotaHoy(db)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS clientes")
        db.execSQL("DROP TABLE IF EXISTS cuotas")
        db.execSQL("DROP TABLE IF EXISTS actividades")
        db.execSQL("DROP TABLE IF EXISTS pagos_actividades")

        onCreate(db)
    }

    //funciones de clientes

    fun insertarCliente(
        nombre: String,
        apellido: String,
        dni: String,
        telefono: String?,
        email: String?,
        fichaMedica: Int,
        esSocio: Int,
        proximaFechaPago: String?,
        fechaInscripcion: String
    ): Long {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put("nombre", nombre)
        values.put("apellido", apellido)
        values.put("dni", dni)
        values.put("telefono", telefono)
        values.put("email", email)
        values.put("ficha_medica", fichaMedica)
        values.put("es_socio", esSocio)
        values.put("proxima_fecha_pago", proximaFechaPago)
        values.put("fecha_inscripcion", fechaInscripcion)

        return try {
            db.insertOrThrow("clientes", null, values)   // ⚠ lanza excepción si DNI o EMAIL ya existen
        } catch (e: android.database.sqlite.SQLiteConstraintException) {
            when {
                e.message?.contains("dni") == true -> {
                    // DNI repetido
                    -2L
                }
                e.message?.contains("email") == true -> {
                    // Email repetido
                    -3L
                }
                else -> {
                    -1L // otro error de constraint
                }
            }
        } catch (e: Exception) {
            -1L // error general
        }
    }


    fun actualizarCliente( cliente: Cliente): Int {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put("nombre", cliente.nombre)
        values.put("apellido", cliente.apellido)
        values.put("dni", cliente.dni)
        values.put("telefono", cliente.telefono)
        values.put("email", cliente.email)
        values.put("ficha_medica", cliente.fichaMedica)
        values.put("es_socio", cliente.esSocio)
        values.put("proxima_fecha_pago", cliente.proximaFechaPago)
        values.put("fecha_inscripcion", cliente.fechaInscripcion)

        // Retorna cuántas filas fueron modificadas (0 si no se encontró el cliente)
        return db.update(
            "clientes",
            values,
            "id = ?",
            arrayOf(cliente.id.toString())
        )
    }


    fun buscarClientePorDni(dni: String): Cliente? {
        val db = this.readableDatabase
        val query = "SELECT * FROM clientes WHERE dni = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))

        var cliente: Cliente? = null

        if (cursor.moveToFirst()) {
            cliente = Cliente(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                fichaMedica = cursor.getInt(cursor.getColumnIndexOrThrow("ficha_medica")),
                esSocio = cursor.getInt(cursor.getColumnIndexOrThrow("es_socio")),
                proximaFechaPago = cursor.getString(cursor.getColumnIndexOrThrow("proxima_fecha_pago")),
                fechaInscripcion = cursor.getString(cursor.getColumnIndexOrThrow("fecha_inscripcion"))
            )
        }

        cursor.close()
        return cliente
    }


    fun buscarClientePagoAVencer():  MutableList<Cliente>? {
        val db = this.readableDatabase
        val query = "SELECT * FROM clientes " +
                "WHERE DATE(clientes.proxima_fecha_pago) = DATE('now');"
        val cursor = db.rawQuery(query, arrayOf())

        var clientes = mutableListOf<Cliente>();

        while (cursor.moveToNext()){
            var cliente = Cliente(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                fichaMedica = cursor.getInt(cursor.getColumnIndexOrThrow("ficha_medica")),
                esSocio = cursor.getInt(cursor.getColumnIndexOrThrow("es_socio")),
                proximaFechaPago = cursor.getString(cursor.getColumnIndexOrThrow("proxima_fecha_pago")),
                fechaInscripcion = cursor.getString(cursor.getColumnIndexOrThrow("fecha_inscripcion")),
            )
            clientes.add(cliente)
        }

        cursor.close()
        return clientes
    }

    //funciones de actividades


    fun buscarActividadPorNombre(nombreActividad: String): Actividad? {
        val db = this.readableDatabase
        val query = "SELECT * FROM actividades WHERE nombre = ?"
        val cursor = db.rawQuery(query, arrayOf(nombreActividad))

        var actividad: Actividad? = null
        if (cursor.moveToFirst()) {
            actividad = Actividad(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
            )
        }
        cursor.close()
        db.close()
        return actividad
    }

    fun buscaractividades():  MutableList<Actividad>? {
        val db = this.readableDatabase
        val query = "SELECT * FROM actividades"
        val cursor = db.rawQuery(query, arrayOf())

        var actividades = mutableListOf<Actividad>();

        while (cursor.moveToNext()){
            var actividad = Actividad(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
            )
            actividades.add(actividad)
        }

        cursor.close()
        return actividades
    }

    //funciones de cuota
    fun insertarPago(cuota: Cuota, clienteDni: String): Long {

        val cliente: Cliente? = buscarClientePorDni(clienteDni)

        if(cliente == null){
            throw Exception("El cliente no existe.");
        }

        val db = this.writableDatabase
        val values = ContentValues()

        values.put("cliente_id", cuota.clienteId)
        values.put("fecha", cuota.fecha)
        values.put("precio", cuota.precio)
        values.put("forma_pago", cuota.formaPago)

        // Retorna el ID del cliente insertado o -1 si falla

        val calendar = Calendar.getInstance()   // fecha actual
        calendar.add(Calendar.MONTH, 1)         // sumar 1 mes

        val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fechaMasUnMes = formato.format(calendar.time)

        val clienteActualizado = Cliente(
            cliente.id,
            cliente.nombre,
            cliente.apellido,
            cliente.dni,
            cliente.telefono,
            cliente.email,
            cliente.fichaMedica,
            1,
            fechaMasUnMes,
            cliente.fechaInscripcion
        );
        var result = -1L;
        val clienteResult = actualizarCliente(clienteActualizado);
        if(clienteResult != -1){
            values.put("cliente_id", cuota.clienteId)
            values.put("fecha", cuota.fecha)
            values.put("precio", cuota.precio)
            values.put("forma_pago", cuota.formaPago)

            // Retorna el ID del cliente insertado o -1 si falla
            result = db.insert("cuotas", null, values)
        }
        return result;
    }


    //funciones de pago actividad
    fun insertarPagoActividad(
        pagoActividad : PagoActividad
    ): Long {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put("cliente_id", pagoActividad.clienteId)
        values.put("fecha", pagoActividad.fecha)
        values.put("actividad_id", pagoActividad.actividadId)

        // Retorna el ID del cliente insertado o -1 si falla
        return db.insert("pagos_actividades", null, values)
    }

    // metodos de usuario
    fun buscarUsuarioPorNombre(nombre: String): User? {
        try{
            val db = this.readableDatabase
            val query = "SELECT * FROM usuarios WHERE nombre = ?"

            val cursor = db.rawQuery(query, arrayOf(nombre))

            var user: User? = null

            if (cursor.moveToFirst()) {
                user = User(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    pass = cursor.getString(cursor.getColumnIndexOrThrow("pass"))
                )
            }

            cursor.close()
            return user
        } catch (e: android.database.sqlite.SQLiteException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return null
    }

    fun insertarClientesMockCuotaHoy(db: SQLiteDatabase) {
        val hoy = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(Date())

        val clientesMock = listOf(
            ClienteMock("Ana", "Gomez", "12345678", "1111-1111", "ana@mail.com"),
            ClienteMock("Luis", "Perez", "23456789", "2222-2222", "luis@mail.com"),
            ClienteMock("Carla", "Diaz", "34567890", "3333-3333", "carla@mail.com")
        )

        for (c in clientesMock) {
            val values = ContentValues().apply {
                put("nombre", c.nombre)
                put("apellido", c.apellido)
                put("dni", c.dni)
                put("telefono", c.telefono)
                put("email", c.email)
                put("ficha_medica", 1)
                put("es_socio", 1)
                put("proxima_fecha_pago", hoy)
                put("fecha_inscripcion", hoy)
            }

            db.insert("clientes", null, values)
        }
    }

}