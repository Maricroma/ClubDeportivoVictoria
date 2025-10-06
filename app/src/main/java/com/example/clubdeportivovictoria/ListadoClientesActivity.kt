package com.example.clubdeportivovictoria

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListadoClientesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_clientes)

        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val btnImprimir = findViewById<Button>(R.id.btnImprimir)

        btnAtras.setOnClickListener {
            finish()
        }

        btnImprimir.setOnClickListener {
            Toast.makeText(this, "Listado enviado a imprimir", Toast.LENGTH_SHORT).show()
            // Por ahora no imprime nada — más adelante se agregará la lógica
        }
    }
}
