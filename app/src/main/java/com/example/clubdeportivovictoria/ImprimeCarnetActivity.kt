package com.example.clubdeportivovictoria

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ImprimeCarnetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imprime_carnet)

        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val btnSi = findViewById<Button>(R.id.btnSi)
        val btnNo = findViewById<Button>(R.id.btnNo)

        btnAtras.setOnClickListener {
            finish()
        }

        btnSi.setOnClickListener {
            Toast.makeText(this, "Carnet enviado a imprimir", Toast.LENGTH_SHORT).show()
            // más adelante se podría agregar aquí la lógica para generar el PDF o imagen
        }

        btnNo.setOnClickListener {
            Toast.makeText(this, "Operación finalizada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
