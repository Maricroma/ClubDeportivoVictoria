package com.example.clubdeportivovictoria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnSalir = findViewById<Button>(R.id.btnSalir)
        val tvBienvenida: TextView = findViewById(R.id.txtBienvenida)

        val nombre = intent.getStringExtra("nombre")

        tvBienvenida.text = "Bienvenido/a, $nombre"

        val btnVerClientes = findViewById<Button>(R.id.btnVerClientes)
        btnVerClientes.setOnClickListener {
            val intent = Intent(this, ListadoClientesActivity::class.java)
            startActivity(intent)
        }

        val btnPagoCuota = findViewById<Button>(R.id.btnPagoCuota)
        btnPagoCuota.setOnClickListener {
            val intent = Intent(this, PagoCuotaActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
