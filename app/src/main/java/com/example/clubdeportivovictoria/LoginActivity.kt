package com.example.clubdeportivovictoria

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usuario = findViewById<EditText>(R.id.txtUsuario)
        val contrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val user = usuario.text.toString()
            val pass = contrasena.text.toString()
            Toast.makeText(this, "Usuario: $user", Toast.LENGTH_SHORT).show()
        }
    }
}
