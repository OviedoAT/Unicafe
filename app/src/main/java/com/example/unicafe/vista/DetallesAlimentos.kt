package com.example.unicafe.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.unicafe.MainActivity
import com.example.unicafe.R

class DetallesAlimentos : AppCompatActivity() {

    private lateinit var btnShopDetailAliments: Button
    private lateinit var btnMenuDetailAliements: Button
    private lateinit var imgDetailAliments: ImageView
    private lateinit var txtNombDetailAliments: TextView
    private lateinit var txtPrecioDetailAliments: TextView
    private lateinit var txtDescripDetailAliments: TextView
    private lateinit var btnOrdenarDetailAliments: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_alimentos)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, ins ->
            val s = ins.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, s.bottom)
            ins
        }

        btnShopDetailAliments = findViewById(R.id.btnShopDetailAliments)
        btnMenuDetailAliements = findViewById(R.id.btnMenuDetailAliements)
        imgDetailAliments = findViewById(R.id.imgDetailAliments)
        txtNombDetailAliments = findViewById(R.id.txtNombDetailAliments)
        txtPrecioDetailAliments = findViewById(R.id.txtPrecioDetailAliments)
        txtDescripDetailAliments = findViewById(R.id.txtDescripDetailAliments)
        btnOrdenarDetailAliments = findViewById(R.id.btnOrdenarDetailAliments)

        val nombre = intent.getStringExtra("NOMBRE") ?: "Producto"
        val descripcion = intent.getStringExtra("DESCRIPCION") ?: ""
        val precio = intent.getDoubleExtra("PRECIO", 0.0)
        val urlImagen = intent.getStringExtra("IMAGEN_URL")

        txtNombDetailAliments.text = nombre
        txtDescripDetailAliments.text = descripcion
        txtPrecioDetailAliments.text = String.format("$ %.2f", precio)

        Glide.with(this)
            .load(urlImagen)
            .into(imgDetailAliments)

        btnOrdenarDetailAliments.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Orden realizada")
                .setMessage("Tu orden de $nombre ha sido enviada.")
                .setPositiveButton("Aceptar", null)
                .show()
        }

        btnMenuDetailAliements.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Menú")
                .setItems(arrayOf("Continuar comprando", "Cerrar sesión")) { _, option ->
                    if (option == 0) {
                        startActivity(Intent(this, Alimentos::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }.show()
        }
    }
}