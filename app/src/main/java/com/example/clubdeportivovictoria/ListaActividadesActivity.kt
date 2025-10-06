package com.example.clubdeportivovictoria

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ListaActividadesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_actividades)

        val btnAtras = findViewById<MaterialButton>(R.id.btnAtras)
        val btnInscribir = findViewById<MaterialButton>(R.id.btnInscribir)

        val cbFila1 = findViewById<CheckBox>(R.id.cbFila1)
        val cbFila2 = findViewById<CheckBox>(R.id.cbFila2)

        // Volver al menú
        btnAtras.setOnClickListener {
            finish()
        }

        btnInscribir.setOnClickListener {
            val seleccionadas = mutableListOf<String>()
            if (cbFila1.isChecked) seleccionadas.add("Fútbol")
            if (cbFila2.isChecked) seleccionadas.add("Natación")

            if (seleccionadas.isEmpty()) {
                Toast.makeText(this, "Seleccione al menos una actividad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, InscriptionActivity::class.java)
            intent.putStringArrayListExtra("actividadesSeleccionadas", ArrayList(seleccionadas))
            startActivity(intent)
        }
    }
}
