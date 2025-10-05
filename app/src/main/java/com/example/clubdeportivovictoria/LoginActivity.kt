package com.example.clubdeportivovictoria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsuario: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var btnIngresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsuario = findViewById(R.id.txtUsuario)
        txtContrasena = findViewById(R.id.txtContrasena)
        btnIngresar = findViewById(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val usuario = txtUsuario.text.toString().trim()
            val contrasena = txtContrasena.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Complete usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // creamos un usuario mockeado para poder ingresar
            val usuarioMock = User(nombre = usuario, email = "$usuario@test.com")

            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("nombre", usuarioMock.nombre)
            }
            startActivity(intent)
            finish()
        }
    }
}

