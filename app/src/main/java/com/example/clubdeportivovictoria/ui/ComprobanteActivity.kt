package com.example.clubdeportivovictoria.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.dao.ServiceLocator

class ComprobanteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.init(this)
        ServiceLocator.init(this)

        setContentView(R.layout.activity_comprobante)

        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val btnSi = findViewById<Button>(R.id.btnSi)
        val btnNo = findViewById<Button>(R.id.btnNo)

        btnAtras.setOnClickListener {
            finish()
        }

        val options = ActivityOptions
            .makeCustomAnimation(this, R.anim.slide_in_slow, R.anim.slide_out_slow)

        btnSi.setOnClickListener {
            Toast.makeText(this, "Comprobante enviado a imprimir", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent, options.toBundle())
        }

        btnNo.setOnClickListener {
            Toast.makeText(this, "Operación finalizada", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent, options.toBundle())
        }
    }
}
