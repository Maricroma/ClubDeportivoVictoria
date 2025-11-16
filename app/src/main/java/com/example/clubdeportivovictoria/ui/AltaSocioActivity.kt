package com.example.clubdeportivovictoria.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.dao.ServiceLocator
import com.example.clubdeportivovictoria.data.models.Cliente
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import android.util.Patterns

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
        ServiceLocator.init(this)


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


            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos obligatorios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!radioSi.isChecked && !radioNo.isChecked) {
                Toast.makeText(this, "Debe indicar si presenta ficha médica", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (telefono.isNotEmpty() && !telefono.matches(Regex("\\d+"))) {
                Toast.makeText(this, "El teléfono solo debe contener números", Toast.LENGTH_LONG).show()
                inputTelefono.error = "Sólo números"
                return@setOnClickListener
            }

            if (!dni.matches(Regex("\\d{8}"))) {
                Toast.makeText(this, "El DNI debe tener 8 dígitos numéricos", Toast.LENGTH_LONG).show()
                inputDni.error = "DNI inválido"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un email válido", Toast.LENGTH_LONG).show()
                inputEmail.error = "Email inválido"
                return@setOnClickListener
            }

            val presentaFicha = radioSi.isChecked

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, 1)

            val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val fechaMasUnMes = formato.format(calendar.time)
            val fechaActual = formato.format(Date())

            val esSocio = intent.getBooleanExtra("esSocio", false)

            val cliente = Cliente(
                id = null,
                nombre = nombre,
                apellido = apellido,
                dni = dni,
                telefono = if (telefono.isEmpty()) null else telefono, // opcional
                email = email,
                fichaMedica = if (presentaFicha) 1 else 0,
                esSocio = if (esSocio) 1 else 0,
                proximaFechaPago = if (esSocio) fechaMasUnMes else null,
                fechaInscripcion = fechaActual
            )

            val result = ServiceLocator.clienteDao.insertarCliente(cliente)

            val options = ActivityOptions
                .makeCustomAnimation(this, R.anim.slide_in_slow, R.anim.slide_out_slow)
            when (result) {
                -2L -> Toast.makeText(this, "El DNI ya está registrado", Toast.LENGTH_LONG).show()
                -3L -> Toast.makeText(this, "El email ya está registrado", Toast.LENGTH_LONG).show()
                -1L -> Toast.makeText(this, "Error al guardar. Intente más tarde", Toast.LENGTH_LONG).show()
                else -> {
                    if (esSocio) {
                        startActivity(Intent(this, ImprimeCarnetActivity::class.java), options.toBundle())
                    } else {
                        Toast.makeText(this, "Cliente guardado con éxito", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MenuActivity::class.java), options.toBundle())
                    }

                    finish()
                }
            }
        }
    }
}
