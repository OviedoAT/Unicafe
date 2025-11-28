package com.example.unicafe.vista

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.unicafe.R

class DetallesAlimentos : AppCompatActivity() {

    private lateinit var imgProductoDetalle: ImageView
    private lateinit var txtNombreDetalle: TextView
    private lateinit var txtDescripcionDetalle: TextView
    private lateinit var txtPrecioDetalle: TextView
    private lateinit var btnAgregarCarrito: Button
    private lateinit var btnVolver: Button

    private var productoId: Int = 0
    private var productoNombre: String = ""
    private var productoDescripcion: String = ""
    private var productoPrecio: Double = 0.0
    private var productoImagen: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_alimentos)

        // Inicializar vistas
        inicializarVistas()

        // Obtener datos del Intent
        obtenerDatosProducto()

        // Mostrar datos
        mostrarDetallesProducto()

        // Configurar listeners
        configurarListeners()
    }

    private fun inicializarVistas() {
        imgProductoDetalle = findViewById(R.id.imgDetailAliments)
        txtNombreDetalle = findViewById(R.id.txtNombDetailAliments)
        txtDescripcionDetalle = findViewById(R.id.txtDescripDetailAliments)
        txtPrecioDetalle = findViewById(R.id.txtPrecioDetailAliments)
        btnAgregarCarrito = findViewById(R.id.btnShopDetailAliments)
        btnVolver = findViewById(R.id.btnMenuDetailAliements)
    }

    private fun obtenerDatosProducto() {
        productoId = intent.getIntExtra("PRODUCTO_ID", 0)
        productoNombre = intent.getStringExtra("PRODUCTO_NOMBRE") ?: ""
        productoDescripcion = intent.getStringExtra("PRODUCTO_DESCRIPCION") ?: ""
        productoPrecio = intent.getDoubleExtra("PRODUCTO_PRECIO", 0.0)
        productoImagen = intent.getStringExtra("PRODUCTO_IMAGEN") ?: ""
    }

    private fun mostrarDetallesProducto() {
        txtNombreDetalle.text = productoNombre
        txtDescripcionDetalle.text = productoDescripcion
        txtPrecioDetalle.text = "$$productoPrecio"

    }

    private fun configurarListeners() {
        btnAgregarCarrito.setOnClickListener {
            agregarAlCarrito()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun agregarAlCarrito() {
        // Aquí implementarías la lógica para agregar al carrito
        Toast.makeText(
            this,
            "Producto '$productoNombre' agregado al carrito",
            Toast.LENGTH_SHORT
        ).show()

        // Opcional: volver a la pantalla anterior
        // finish()
    }
}