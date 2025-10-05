package com.example.clubdeportivovictoria

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class ComprobanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comprobante)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSi = findViewById<MaterialButton>(R.id.btnSi)
        val btnNo = findViewById<MaterialButton>(R.id.btnNo)
        val tvTitulo = findViewById<TextView>(R.id.tvTitulo)

        val tipoOperacion = intent.getStringExtra("tipoOperacion") ?: ""
        val nombreCliente = intent.getStringExtra("nombreCliente") ?: ""
        val importe = intent.getStringExtra("importe") ?: ""

        tvTitulo.text = "Imprimir comprobante de $tipoOperacion\n$nombreCliente\n$ $importe"

        btnSi.setOnClickListener {
            finish()
        }

        btnNo.setOnClickListener {
            finish()
        }
    }
}
