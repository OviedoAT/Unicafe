package com.example.unicafe.vista

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.unicafe.R

class DetallesAlimentos : AppCompatActivity() {

    private lateinit var btnShopDetailAliments: ImageButton
    private lateinit var btnMenuDetailAliements: ImageButton
    private lateinit var imgDetailAliments: ImageView
    private lateinit var txtNombDetailAliments: TextView
    private lateinit var txtPrecioDetailAliments: TextView
    private lateinit var txtDescripDetailAliments: TextView
    private lateinit var btnOrdenarDetailAliments: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_alimentos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnShopDetailAliments = findViewById(R.id.btnShopDetailAliments)
        btnMenuDetailAliements = findViewById(R.id.btnMenuDetailAliements)
        imgDetailAliments = findViewById(R.id.imgDetailAliments)
        txtNombDetailAliments = findViewById(R.id.txtNombDetailAliments)
        txtPrecioDetailAliments = findViewById(R.id.txtPrecioDetailAliments)
        txtDescripDetailAliments = findViewById(R.id.txtDescripDetailAliments)
        btnOrdenarDetailAliments = findViewById(R.id.btnOrdenarDetailAliments)

        // Recibir datos del Intent
        val nombre = intent.getStringExtra("NOMBRE") ?: "Producto"
        val descripcion = intent.getStringExtra("DESCRIPCION") ?: ""
        val precio = intent.getDoubleExtra("PRECIO", 0.0)
        val imagenUrl = intent.getStringExtra("IMAGEN_URL") ?: ""

        txtNombDetailAliments.text = nombre
        txtPrecioDetailAliments.text = "$ $precio"
        txtDescripDetailAliments.text = descripcion

        if (imagenUrl.isNotBlank()) {
            Glide.with(this)
                .load(imagenUrl)
                .into(imgDetailAliments)
        }

        btnMenuDetailAliements.setOnClickListener {
            finish()
        }

        btnShopDetailAliments.setOnClickListener {
            Toast.makeText(this, "Carrito pendiente de implementar", Toast.LENGTH_SHORT).show()
        }

        btnOrdenarDetailAliments.setOnClickListener {
            // Aquí podría ir una llamada a API para generar un pedido
            Toast.makeText(this, "Orden registrada (pendiente implementar en BD)", Toast.LENGTH_SHORT).show()
        }
    }
}