package com.example.clubdeportivovictoria.ui

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.dao.ServiceLocator
import com.example.clubdeportivovictoria.data.models.Actividad
import com.google.android.material.button.MaterialButton

class ListaActividadesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_actividades)
        ServiceLocator.init(this)

        val btnAtras = findViewById<MaterialButton>(R.id.btnAtras)
        val btnInscribir = findViewById<MaterialButton>(R.id.btnInscribir)

//        val cbFila1 = findViewById<CheckBox>(R.id.cbFila1)
//        val cbFila2 = findViewById<CheckBox>(R.id.cbFila2)

        val actividades = ServiceLocator.actividadDao.listarTodos();
        val layoutActividades = findViewById<LinearLayout>(R.id.layoutActividades)

        if (actividades != null) {
            for ((index, actividad) in actividades.withIndex()) {
                agregarFilaActividad(layoutActividades, actividad, index + 1)
            }
        }

        // Volver al menú
        btnAtras.setOnClickListener {
            finish()
        }

        btnInscribir.setOnClickListener {
            val seleccionadas = mutableListOf<String>()
            var total = 0.0
            for (i in 0 until layoutActividades.childCount) {
                val fila = layoutActividades.getChildAt(i) as LinearLayout
                // Buscamos el primer CheckBox dentro de la fila
                val cb = fila.children.filterIsInstance<CheckBox>().firstOrNull()
                // Buscamos el TextView con el nombre de la actividad
                val tvNombre = fila.children
                    .filterIsInstance<TextView>()
                    .firstOrNull { (it.tag as? String)?.startsWith("nombre_") == true }

                val tvPrecio = fila.children
                    .filterIsInstance<TextView>()
                    .firstOrNull { (it.tag as? String)?.startsWith("precio_") == true }

                if (cb?.isChecked == true && tvNombre != null) {
                    seleccionadas.add(tvNombre.text.toString())
                    total += tvPrecio?.text
                        .toString()
                        .replace(Regex("[^0-9.,]"), "")   // quita $ y cualquier otro símbolo
                        .replace(",", ".")               // reemplaza coma por punto si existe
                        .toDoubleOrNull() ?: 0.0         // si falla, suma 0
                }
            }

            if (seleccionadas.isEmpty()) {
                Toast.makeText(this, "Seleccione al menos una actividad", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val options = ActivityOptions
                .makeCustomAnimation(this, R.anim.slide_in_slow, R.anim.slide_out_slow)


            val intent = Intent(this, PagoCuotaActivity::class.java)
            intent.putStringArrayListExtra("actividadesSeleccionadas", ArrayList(seleccionadas))
            intent.putExtra("esSocio", false)
            intent.putExtra("total", total)
            startActivity(intent, options.toBundle())
        }


    }

    fun agregarFilaActividad(parent: LinearLayout, actividad: Actividad, numero: Int) {
        val context = parent.context

        val fila = LinearLayout(context).apply {
            tag = "fila_${actividad.id}"  // tag único por fila
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dpToPx(context, 4) }
            setBackgroundColor(Color.parseColor("#9683F5"))
            setPadding(dpToPx(context, 4), dpToPx(context, 4), dpToPx(context, 4), dpToPx(context, 4))
            gravity = Gravity.CENTER_VERTICAL
        }

        // CheckBox
        val checkBox = CheckBox(context).apply {
            tag = "cb_${actividad.id}"
            buttonTintList = ColorStateList.valueOf(Color.WHITE)
        }

        // Número
        val tvNumero = TextView(context).apply {
            text = numero.toString()
            width = dpToPx(context, 60)
            height = dpToPx(context, 40)
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
        }

        // Nombre
        val tvNombre = TextView(context).apply {
            text = actividad.nombre
            tag = "nombre_${actividad.id}"
            width = dpToPx(context, 100)
            height = dpToPx(context, 40)
            gravity = Gravity.CENTER
            setPadding(dpToPx(context, 4), 0, 0, 0)
            setTextColor(Color.WHITE)
        }

        // Precio
        val tvPrecio = TextView(context).apply {
            tag = "precio_${actividad.id}"
            text = "$${actividad.precio}"
            width = dpToPx(context, 100)
            height = dpToPx(context, 40)
            gravity = Gravity.CENTER
            setPadding(dpToPx(context, 4), 0, 0, 0)
            setTextColor(Color.WHITE)
        }

        fila.addView(checkBox)
        fila.addView(tvNumero)
        fila.addView(tvNombre)
        fila.addView(tvPrecio)

        parent.addView(fila)
    }

    fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}
