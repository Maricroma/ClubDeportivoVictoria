package com.example.clubdeportivovictoria.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.dao.ServiceLocator

class ListadoClientesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_clientes)
        ServiceLocator.init(this)

        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val btnImprimir = findViewById<Button>(R.id.btnImprimir)
        val recycler = findViewById<RecyclerView>(R.id.recyclerClientes)

        val clientes = ServiceLocator.clienteDao.clientesMorosos() ?: emptyList()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ClientesAdapter(clientes)

        btnAtras.setOnClickListener { finish() }

        btnImprimir.setOnClickListener {
            Toast.makeText(this, "Listado enviado a imprimir", Toast.LENGTH_LONG).show()
        }
    }
}

