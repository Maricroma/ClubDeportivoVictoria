package com.example.clubdeportivovictoria

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AltaSocioActivity : AppCompatActivity() {

    private lateinit var inputDni: EditText
    private lateinit var inputNombre: EditText
    private lateinit var inputApellido: EditText
    private lateinit var inputTelefono: EditText
    private lateinit var inputEmail: EditText
    private lateinit var radioSi: RadioButton
    private lateinit var radioNo: RadioButton
    private lateinit var btnGuardarSocio: Button
    private lateinit var btnAtras: Button
    private var esSocio: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta_socio)

        inputDni = findViewById(R.id.inputDni)
        inputNombre = findViewById(R.id.inputNombre)
        inputApellido = findViewById(R.id.inputApellido)
        inputTelefono = findViewById(R.id.inputTelefono)
        inputEmail = findViewById(R.id.inputEmail)
        radioSi = findViewById(R.id.radioSi)
        radioNo = findViewById(R.id.radioNo)
        btnGuardarSocio = findViewById(R.id.btnGuardarSocio)
        btnAtras = findViewById(R.id.btnAtras)
        esSocio = intent.getBooleanExtra("esSocio", false)

        btnAtras.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnGuardarSocio.setOnClickListener {
            val dni = inputDni.text.toString().trim()
            val nombre = inputNombre.text.toString().trim()
            val apellido = inputApellido.text.toString().trim()
            val telefono = inputTelefono.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val presentaFicha = radioSi.isChecked

            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
                telefono.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Acá más adelante iría la lógica para guardar el socio en la BD
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()

                if (esSocio) {
                    // Si es socio, mostrar la pantalla de imprimir carnet
                    val intent = Intent(this, ImprimeCarnetActivity::class.java)
                    startActivity(intent)
                } else {
                    // Si NO es socio, volver directamente al menú principal
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }
    }
}
