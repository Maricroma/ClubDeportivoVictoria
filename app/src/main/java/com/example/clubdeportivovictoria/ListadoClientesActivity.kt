package com.example.clubdeportivovictoria

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ListadoClientesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_clientes)

        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val btnImprimir = findViewById<Button>(R.id.btnImprimir)

        btnAtras.setOnClickListener {
            finish() // vuelve al menú anterior
        }

        btnImprimir.setOnClickListener {
            // Por ahora no imprime nada — más adelante se agregará la lógica
        }
    }
}
