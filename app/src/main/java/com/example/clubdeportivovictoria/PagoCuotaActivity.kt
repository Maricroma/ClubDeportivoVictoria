package com.example.clubdeportivovictoria

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout

class PagoCuotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pago_cuota)

        // Asegurate de que el ConstraintLayout raíz tenga android:id="@+id/main"
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnImprimir = findViewById<MaterialButton>(R.id.btnImprimir)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val etImporte = findViewById<EditText>(R.id.etImporte)

        btnImprimir.setOnClickListener {
            val intent = Intent(this, ComprobanteActivity::class.java)
            intent.putExtra("tipoOperacion", "Pago de cuota")
            intent.putExtra("nombreCliente", tvNombre.text.toString())
            intent.putExtra("importe", etImporte.text.toString())
            startActivity(intent)
        }
    }
}
