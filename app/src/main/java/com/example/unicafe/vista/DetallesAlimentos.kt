package com.example.unicafe.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.unicafe.MainActivity
import com.example.unicafe.R

class DetallesAlimentos : AppCompatActivity() {

    private lateinit var imgProductoDet: ImageView
    private lateinit var txtNombreDet: TextView
    private lateinit var txtDescripcionDet: TextView
    private lateinit var txtPrecioDet: TextView

    private lateinit var btnMenuDet: Button
    private lateinit var btnComprasDet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_alimentos)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgProductoDet = findViewById(R.id.imgDetailAliments)
        txtNombreDet = findViewById(R.id.txtNombDetailAliments)
        txtDescripcionDet = findViewById(R.id.txtDescripDetailAliments)
        txtPrecioDet = findViewById(R.id.txtPrecioDetailAliments)

        btnMenuDet = findViewById(R.id.btnMenuDetailAliements)
        btnComprasDet = findViewById(R.id.btnShopDetailAliments)

        val idProducto = intent.getIntExtra("idProducto", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val descripcion = intent.getStringExtra("descripcion") ?: ""
        val precio = intent.getDoubleExtra("precio", 0.0)
        val urlImagen = intent.getStringExtra("url_imagen")

        if (idProducto == -1) {
            Toast.makeText(this, "No se recibieron datos del producto", Toast.LENGTH_LONG).show()
        }

        txtNombreDet.text = nombre
        txtDescripcionDet.text = descripcion
        txtPrecioDet.text = "$${String.format("%.2f", precio)}"

        if (!urlImagen.isNullOrEmpty()) {
            Glide.with(this)
                .load(urlImagen)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgProductoDet)
        } else {
            imgProductoDet.setImageResource(R.mipmap.ic_launcher)
        }

        btnMenuDet.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Menú")
                .setItems(arrayOf("Volver a alimentos", "Cerrar sesión")) { _, option ->
                    when (option) {
                        0 -> finish()
                        1 -> {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                .show()
        }

        btnComprasDet.setOnClickListener {
            Toast.makeText(this, "Carrito aún no implementado", Toast.LENGTH_SHORT).show()
        }
    }
}