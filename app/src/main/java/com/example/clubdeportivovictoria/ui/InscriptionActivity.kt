package com.example.clubdeportivovictoria.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.dao.ServiceLocator

class InscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inscription)
        ServiceLocator.init(this)

        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val options = ActivityOptions
            .makeCustomAnimation(this, R.anim.slide_in_slow, R.anim.slide_out_slow)

        val btnImprimir = findViewById<MaterialButton>(R.id.btnImprimir)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvFechaVencimiento = findViewById<TextView>(R.id.tvFechaVencimiento)
        val btnAtras = findViewById<MaterialButton>(R.id.btnAtras)
        tvFechaVencimiento.visibility = View.GONE

        btnImprimir.setOnClickListener {
            val intent = Intent(this, ComprobanteActivity::class.java)
            intent.putExtra("tipoOperacion", "Inscripción")
            intent.putExtra("nombreCliente", tvNombre.text.toString())

            startActivity(intent, options.toBundle())
        }

        btnAtras.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}