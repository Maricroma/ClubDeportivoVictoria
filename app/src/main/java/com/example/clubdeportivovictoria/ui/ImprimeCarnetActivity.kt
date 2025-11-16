package com.example.clubdeportivovictoria.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivovictoria.R

class ImprimeCarnetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imprime_carnet)

        val options = ActivityOptions
            .makeCustomAnimation(this, R.anim.slide_in_slow, R.anim.slide_out_slow)

        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val btnSi = findViewById<Button>(R.id.btnSi)
        val btnNo = findViewById<Button>(R.id.btnNo)


        btnAtras.setOnClickListener {
            finish()
        }

        btnSi.setOnClickListener {
            Toast.makeText(this, "Carnet enviado a imprimir", Toast.LENGTH_LONG).show()
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
