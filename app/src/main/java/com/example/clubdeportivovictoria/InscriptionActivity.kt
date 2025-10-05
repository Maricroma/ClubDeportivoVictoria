package com.example.clubdeportivovictoria

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.EditText

class InscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inscription)

        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnImprimir = findViewById<MaterialButton>(R.id.btnImprimir)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvFechaVencimiento = findViewById<TextView>(R.id.tvFechaVencimiento)
        tvFechaVencimiento.visibility = View.GONE

        btnImprimir.setOnClickListener {
            val intent = Intent(this, ComprobanteActivity::class.java)
            intent.putExtra("tipoOperacion", "Inscripción")
            intent.putExtra("nombreCliente", tvNombre.text.toString())
            // No incluimos importe porque es inscripción
            startActivity(intent)
        }
    }
}