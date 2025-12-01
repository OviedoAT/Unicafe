package com.example.unicafe.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unicafe.MainActivity
import com.example.unicafe.R
import com.example.unicafe.contrato.ProductosContrato
import com.example.unicafe.modelo.dto.Producto
import com.example.unicafe.presentador.ProductosPresentador

class Alimentos : AppCompatActivity(), ProductosContrato.Vista {

    private lateinit var btnComprasAlimen: Button
    private lateinit var btnMenuAlimen: Button
    private lateinit var btnCombos: Button
    private lateinit var btnPromos: Button
    private lateinit var btnTrending: Button
    private lateinit var btnComidaCal: Button

    private lateinit var recyclerCombos: RecyclerView
    private lateinit var recyclerPromos: RecyclerView
    private lateinit var recyclerTrending: RecyclerView

    private lateinit var adapterCombos: ProductoAdapter
    private lateinit var adapterPromos: ProductoAdapter
    private lateinit var adapterTrending: ProductoAdapter

    private lateinit var presentador: ProductosPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alimentos)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, ins ->
            val s = ins.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, s.bottom)
            ins
        }

        presentador = ProductosPresentador(this)

        btnComprasAlimen = findViewById(R.id.btnComprasAlimen)
        btnMenuAlimen = findViewById(R.id.btnMenuAlimen)
        btnCombos = findViewById(R.id.btnCombos)
        btnPromos = findViewById(R.id.btnPromos)
        btnTrending = findViewById(R.id.btnTrending)
        btnComidaCal = findViewById(R.id.btnComidaCal)

        recyclerCombos = findViewById(R.id.recyclerCombos)
        recyclerPromos = findViewById(R.id.recyclerPromos)
        recyclerTrending = findViewById(R.id.recyclerTrending)

        recyclerCombos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerPromos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerTrending.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        adapterCombos = ProductoAdapter(emptyList())
        adapterPromos = ProductoAdapter(emptyList())
        adapterTrending = ProductoAdapter(emptyList())

        recyclerCombos.adapter = adapterCombos
        recyclerPromos.adapter = adapterPromos
        recyclerTrending.adapter = adapterTrending

        btnMenuAlimen.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Menú")
                .setItems(arrayOf("Seguir comprando", "Cerrar sesión")) { _, option ->
                    if (option == 1) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }.show()
        }

        presentador.cargarProductos()
    }

    override fun mostrarCargando(mostrar: Boolean) {}

    override fun mostrarProductos(lista: List<Producto>) {
        adapterCombos.actualizarDatos(lista)
        adapterPromos.actualizarDatos(lista)
        adapterTrending.actualizarDatos(lista)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
