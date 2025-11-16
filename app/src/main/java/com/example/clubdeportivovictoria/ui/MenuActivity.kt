package com.example.clubdeportivovictoria.ui

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivovictoria.R

class MenuActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnSalir = findViewById<Button>(R.id.btnSalir)
        prefs = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)

        intent.getStringExtra("nombre")?.let { nombre ->
            prefs.edit().putString("nombre", nombre).apply()
        }

        val nombreUsuario = prefs.getString("nombre", "Usuario")
        findViewById<TextView>(R.id.txtBienvenida).text = "Bienvenido/a, $nombreUsuario"

        val options = ActivityOptions
            .makeCustomAnimation(this, R.anim.slide_in_slow, R.anim.slide_out_slow)


        val btnAltaSocio = findViewById<Button>(R.id.btnAltaSocio)
        btnAltaSocio.setOnClickListener {
            val intent = Intent(this, AltaSocioActivity::class.java)
            intent.putExtra("esSocio", true)
            startActivity(intent, options.toBundle())
        }

        val btnAltaNoSocio = findViewById<Button>(R.id.btnAltaNoSocio)
        btnAltaNoSocio.setOnClickListener {
            val intent = Intent(this, AltaSocioActivity::class.java)
            intent.putExtra("esSocio", false)
            startActivity(intent, options.toBundle())
        }

        val btnVerClientes = findViewById<Button>(R.id.btnVerClientes)
        btnVerClientes.setOnClickListener {
            val intent = Intent(this, ListadoClientesActivity::class.java)
            startActivity(intent, options.toBundle())
        }

        val btnPagoCuota = findViewById<Button>(R.id.btnPagoCuota)
        btnPagoCuota.setOnClickListener {
            val intent = Intent(this, PagoCuotaActivity::class.java)
            startActivity(intent, options.toBundle())
        }

        val btnListaActividades = findViewById<Button>(R.id.btnInscribirActividad)
        btnListaActividades.setOnClickListener {
            val intent = Intent(this, ListaActividadesActivity::class.java)
            startActivity(intent, options.toBundle())
        }

        btnSalir.setOnClickListener {
            prefs.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent, options.toBundle())
        }
    }
}
