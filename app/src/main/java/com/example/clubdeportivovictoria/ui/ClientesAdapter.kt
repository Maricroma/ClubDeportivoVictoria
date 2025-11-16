package com.example.clubdeportivovictoria.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.models.Cliente

class ClientesAdapter(private val lista: List<Cliente>) :
    RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder>() {

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreRow)
        val tvTelefono: TextView = itemView.findViewById(R.id.tvTelefonoRow)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmailRow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = lista[position]
        holder.tvNombre.text = cliente.nombre + " " + cliente.apellido
        holder.tvTelefono.text = cliente.telefono ?: "-"
        holder.tvEmail.text = cliente.email
    }

    override fun getItemCount(): Int = lista.size
}
