package com.example.clubdeportivovictoria.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.clubdeportivovictoria.R
import com.example.clubdeportivovictoria.data.dao.ServiceLocator
import com.example.clubdeportivovictoria.data.models.Cliente
import com.example.clubdeportivovictoria.data.models.Cuota
import com.example.clubdeportivovictoria.data.models.PagoActividad
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PagoCuotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pago_cuota)
        ServiceLocator.init(this)
        var cliente: Cliente? = null
        var ValidoFichaMedica: Int? = 0

        try{

            val layoutFormaPago = findViewById<LinearLayout>(R.id.layoutFormaPago)
            val rdbFichaMedica = findViewById<RadioButton>(R.id.rbFichaMedica)
            val btnInscribir = findViewById<MaterialButton>(R.id.btnCobrar)
            layoutFormaPago.visibility = View.GONE
            btnInscribir.visibility = View.GONE

            rdbFichaMedica.visibility = View.GONE

            val mainLayout = findViewById<ConstraintLayout>(R.id.main)
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            val actividadesSeleccionadas = intent.getStringArrayListExtra("actividadesSeleccionadas")
            val total = intent.getDoubleExtra("total", 50000.0)


            val buscarBtn = findViewById<ImageView>(R.id.btnBuscar)
            val tvNombre = findViewById<TextView>(R.id.tvNombre)
            val etImporte = findViewById<TextView>(R.id.etImporte)
            val btnAtras = findViewById<MaterialButton>(R.id.btnAtras)

            val etDni = findViewById<EditText>(R.id.etDNI)
            val tvNombreValue = findViewById<TextView>(R.id.tvNombreValue)
            val tvTipoClienteValue = findViewById<TextView>(R.id.tvTipoClienteValue)
            val tvEstadoValue = findViewById<TextView>(R.id.tvEstadoValue)
            val tvFechaVencimientoValue = findViewById<TextView>(R.id.tvFechaVencimientoValue)

            btnAtras.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            etImporte.text = total.toString()

            buscarBtn.setOnClickListener {
                cliente = ServiceLocator.clienteDao.buscarPorDni(etDni.text.toString())

                if(cliente != null){
                    ValidoFichaMedica = cliente?.fichaMedica

                    btnInscribir.visibility = View.VISIBLE

                    if (ValidoFichaMedica == 0) {
                        rdbFichaMedica.visibility = View.VISIBLE
                    }

                    if (!actividadesSeleccionadas.isNullOrEmpty()) {
                        if (cliente?.esSocio == 1) {
                            layoutFormaPago.visibility = View.GONE
                        } else {
                            layoutFormaPago.visibility = View.VISIBLE
                        }
                    }

                    if (!actividadesSeleccionadas.isNullOrEmpty()) {
                        // Hay actividades seleccionadas
                        if (cliente?.esSocio == 1) {
                            layoutFormaPago.visibility = View.GONE
                        } else {
                            layoutFormaPago.visibility = View.VISIBLE
                        }
                    } else {
                        // No hay actividades seleccionadas → SIEMPRE mostrar forma de pago
                        layoutFormaPago.visibility = View.VISIBLE
                    }

                    val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

                    val fechaPagoStr = cliente?.proximaFechaPago
                    var estado = "No aplica"

                    if (!fechaPagoStr.isNullOrEmpty()) {
                        val fechaPago = formato.parse(fechaPagoStr)
                        val fechaActual = Date()

                        estado = if (fechaPago.before(fechaActual)) {
                            "En mora"
                        } else {
                            "Al día"
                        }
                    }

                    tvNombreValue.text = cliente?.nombre;
                    tvTipoClienteValue.text =     if (cliente?.esSocio == 1) "Socio" else "No socio";
                    tvEstadoValue.text = estado;
                    tvFechaVencimientoValue.text =
                        cliente?.proximaFechaPago?.takeIf { it.isNotBlank() } ?: "                                              "
                    tvFechaVencimientoValue.text = tvFechaVencimientoValue.text.toString() + "     "
                }else {
                    tvNombreValue.text = "                                              ";
                    tvTipoClienteValue.text = "                                              ";
                    tvEstadoValue.text = "                                              ";
                    tvFechaVencimientoValue.text ="                                              ";
                }
            }


            val options = ActivityOptions
                .makeCustomAnimation(this, R.anim.slide_in_slow, R.anim.slide_out_slow)


            val actividadesCount = actividadesSeleccionadas?.count()
            if(actividadesCount == null || actividadesCount == 0){
                btnInscribir.setOnClickListener {
                    val rbEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)
                    val rbTarjeta = findViewById<RadioButton>(R.id.rbTarjeta)
                    val metodoPago = if(rbTarjeta.isChecked) "tarjeta" else if (rbEfectivo.isChecked) "efectivo" else null;
                    val presnetoFicha = if(rdbFichaMedica.isChecked) true else false;

                    if (ValidoFichaMedica  == 0 && !presnetoFicha) {
                        Toast.makeText(this, "Debe confirmar la entrega de la ficha medica.", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    if (metodoPago != null) {
                        cliente?.let {
                            val esSocio = intent.getBooleanExtra("esSocio", false)
                            val calendar = Calendar.getInstance()   // fecha actual
                            calendar.add(Calendar.MONTH, 1); // sumar 1 mes
                            val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            val fechaMasUnMes = formato.format(calendar.time);

                            val hoy = Calendar.getInstance()
                            // === 1) Obtener fecha de proximaFechaPago de la BD ===
                            val fechaPPago = Calendar.getInstance()
                            if (it.proximaFechaPago != null) {
                                try {
                                    fechaPPago.time = formato.parse(it.proximaFechaPago)!!
                                } catch (e: Exception) {
                                    fechaPPago.time = Date()
                                }
                            } else {
                                fechaPPago.time = Date()
                            }
                            // === 2) Elegir fecha base según tu regla ===
                            val fechaBase = Calendar.getInstance()

                            if (fechaPPago.before(hoy) || fechaPPago.equals(hoy)) {
                                // Si la fecha guardada está vencida o es hoy → usar HOY
                                fechaBase.time = hoy.time
                            } else {
                                // Si la fecha guardada es futura → usar esa fecha
                                fechaBase.time = fechaPPago.time
                            }
                            // === 3) Sumar 1 mes a la fecha seleccionada ===
                            fechaBase.add(Calendar.MONTH, 1)
                            val proximaFechaPagoFinal = formato.format(fechaBase.time)

                            // Fecha actual para registrar la cuota
                            val fechaActual = formato.format(Date())


                            val updatecliente = Cliente(
                                id = it.id, nombre = it.nombre, apellido = it.apellido,
                                dni = it.dni,  telefono = it.telefono, email = it.email,
                                fichaMedica = 1,
                                esSocio =1,
                                proximaFechaPago = proximaFechaPagoFinal,
                                fechaInscripcion = it.fechaInscripcion
                            )

                            val etImporte = findViewById<TextView>(R.id.etImporte)
                            val rbEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)

                            val cuota = Cuota(
                                null, updatecliente.id, fechaActual, etImporte.text.toString().toDouble(),
                                rbEfectivo.isChecked
                            )
                            val result = ServiceLocator.cuotaDao.pagar(cuota, updatecliente.dni);
                            ServiceLocator.clienteDao.actualizarCliente(updatecliente)

                            if(result != -1L){
                                Toast.makeText(this, "Pago ingresado.", Toast.LENGTH_LONG).show();
                                tvNombreValue.text = "                                              ";
                                tvTipoClienteValue.text = "                                              ";
                                tvEstadoValue.text = "                                              ";
                                tvFechaVencimientoValue.text ="                                              ";
                                rbEfectivo.isChecked = false;
                                rbTarjeta .isChecked = false;
                                etImporte.text= "";
                                etDni.setText("");
                            }

                            val intent = Intent(this, ComprobanteActivity::class.java)
                            intent.putExtra("tipoOperacion", "Pago de cuota")
                            intent.putExtra("nombreCliente", tvNombre.text.toString())
                            intent.putExtra("importe", etImporte.text.toString())
                            startActivity(intent, options.toBundle())
                        }
                    }else {
                        Toast.makeText(this, "Debe seleccionar un método de pago", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                btnInscribir.setOnClickListener {
                    val rbEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)
                    val rbTarjeta = findViewById<RadioButton>(R.id.rbTarjeta)
                    val metodoPago = if(rbTarjeta.isChecked) "tarjeta" else if (rbEfectivo.isChecked) "efectivo" else null;
                    val presnetoFicha = if(rdbFichaMedica.isChecked) true else false;

                    if (ValidoFichaMedica  == 0 && !presnetoFicha) {
                        Toast.makeText(this, "Debe confirmar la entrega de la ficha medica.", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    val debeValidarMetodoPago =
                        !(cliente?.esSocio == 1 && !actividadesSeleccionadas.isNullOrEmpty())

                    if (debeValidarMetodoPago && metodoPago == null) {
                        Toast.makeText(this, "Debe seleccionar un método de pago", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    cliente?.let {
                        val esSocio = intent.getBooleanExtra("esSocio", false)
                        val calendar = Calendar.getInstance()   // fecha actual
                        calendar.add(Calendar.MONTH, 1); // sumar 1 mes
                        val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        val fechaMasUnMes = formato.format(calendar.time);
                        val fechaActual = formato.format(Date());

                        val updatecliente = Cliente(
                            id = it.id, nombre = it.nombre, apellido = it.apellido,
                            dni = it.dni,  telefono = it.telefono, email = it.email,
                            fichaMedica = 1,
                            esSocio = it.esSocio,
                            proximaFechaPago = fechaMasUnMes,
                            fechaInscripcion = it.fechaInscripcion
                        )

                        val etImporte = findViewById<TextView>(R.id.etImporte)
                        val rbEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)

                        var count = 0;
                        actividadesSeleccionadas?.forEach { a ->
                            val actividad = ServiceLocator.actividadDao.buscarActividadPorNombre(a)
                            if(actividad != null) {
                                val pagoActividad = PagoActividad(
                                    null, updatecliente.id, fechaActual, actividad.id
                                )
                                if(ServiceLocator.pagoActividadDao.pagarCuota(pagoActividad) != -1L) {
                                    count++
                                }
                                ServiceLocator.clienteDao.actualizarCliente(updatecliente)
                            }
                        }

                        if(count > 0){
                            Toast.makeText(this, "Inscripción exitosa.", Toast.LENGTH_LONG).show();
                            tvNombreValue.text = "                                              ";
                            tvTipoClienteValue.text = "                                              ";
                            tvEstadoValue.text = "                                              ";
                            tvFechaVencimientoValue.text ="                                              ";
                            rbEfectivo.isChecked = false;
                            rbTarjeta .isChecked = false;
                            etImporte.text= "";
                            etDni.setText("");

                            val intent = Intent(this, ComprobanteActivity::class.java)
                            intent.putExtra("tipoOperacion", "Pago de cuota")
                            intent.putExtra("nombreCliente", tvNombre.text.toString())
                            intent.putExtra("importe", etImporte.text.toString())
                            startActivity(intent, options.toBundle())
                        } else {
                            Toast.makeText(this, "No se pudo realizar el pago, intente nuevamente.", Toast.LENGTH_LONG).show();
                            tvNombreValue.text = "                                              ";
                            tvTipoClienteValue.text = "                                              ";
                            tvEstadoValue.text = "                                              ";
                            tvFechaVencimientoValue.text ="                                              ";
                            rbEfectivo.isChecked = false;
                            rbTarjeta .isChecked = false;
                            etImporte.text= "";
                            etDni.setText("");
                        }
                    }
                }
            }
        } catch (e: Exception) {
                -1L // error general
        }
    }
}
