package com.example.clubdeportivovictoria.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.dao.ServiceLocator
import com.example.clubdeportivovictoria.data.models.User

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsuario: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var btnIngresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ServiceLocator.init(this)

        txtUsuario = findViewById(R.id.txtUsuario)
        txtContrasena = findViewById(R.id.txtContrasena)
        btnIngresar = findViewById(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val usuario = txtUsuario.text.toString().trim()
            val contrasena = txtContrasena.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Complete usuario y contraseña", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val user = ServiceLocator.userDao.buscarUsuarioPorNombre(usuario)

            if (user == null) {
                Toast.makeText(this, "Credenciales invalidas.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (user.pass != contrasena) {
                Toast.makeText(this, "Credenciales invalidas.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("nombre", user.nombre)
            }

            startActivity(intent)
            finish()
        }
    }
}

