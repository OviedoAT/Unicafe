package com.example.unicafe.vista

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unicafe.R
import com.example.unicafe.contrato.ProductosContrato
import com.example.unicafe.modelo.dto.Producto
import com.example.unicafe.presentador.ProductosPresentador

class Alimentos : AppCompatActivity(), ProductosContrato.Vista {

    private lateinit var btnComprasAlimen: ImageButton
    private lateinit var btnMenuAlimen: ImageButton
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias
        btnComprasAlimen = findViewById(R.id.btnComprasAlimen)
        btnMenuAlimen = findViewById(R.id.btnMenuAlimen)
        btnCombos = findViewById(R.id.btnCombos)
        btnPromos = findViewById(R.id.btnPromos)
        btnTrending = findViewById(R.id.btnTrending)
        btnComidaCal = findViewById(R.id.btnComidaCal)

        recyclerCombos = findViewById(R.id.recyclerCombos)
        recyclerPromos = findViewById(R.id.recyclerPromos)
        recyclerTrending = findViewById(R.id.recyclerTrending)

        // LayoutManagers horizontales
        recyclerCombos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerPromos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerTrending.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        // Adapters iniciales vacíos
        adapterCombos = ProductoAdapter(emptyList())
        adapterPromos = ProductoAdapter(emptyList())
        adapterTrending = ProductoAdapter(emptyList())

        recyclerCombos.adapter = adapterCombos
        recyclerPromos.adapter = adapterPromos
        recyclerTrending.adapter = adapterTrending

        // Presenter
        presentador = ProductosPresentador(this)
        presentador.cargarProductos()

        // Botones
        btnMenuAlimen.setOnClickListener {
            finish() // vuelve a la pantalla anterior (login por ahora)
        }

        btnComprasAlimen.setOnClickListener {
            Toast.makeText(this, "Carrito pendiente de implementar", Toast.LENGTH_SHORT).show()
        }

        btnCombos.setOnClickListener {
            Toast.makeText(this, "Filtros Combos (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnPromos.setOnClickListener {
            Toast.makeText(this, "Mostrando Promos", Toast.LENGTH_SHORT).show()
        }

        btnTrending.setOnClickListener {
            Toast.makeText(this, "Mostrando Trending", Toast.LENGTH_SHORT).show()
        }

        btnComidaCal.setOnClickListener {
            Toast.makeText(this, "Filtros Comida caliente (pendiente)", Toast.LENGTH_SHORT).show()
        }
    }

    // ===== ProductosContrato.Vista =====

    override fun mostrarCargando(mostrar: Boolean) {
        // Si quieres, aquí podrías mostrar un ProgressBar (si lo agregas al layout)
    }

    override fun mostrarProductos(lista: List<Producto>) {
        // Por ahora: misma lista en los tres RecyclerView
        adapterCombos.actualizarDatos(lista)
        adapterPromos.actualizarDatos(lista)
        adapterTrending.actualizarDatos(lista)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presentador.onDestroy()
        super.onDestroy()
    }
}