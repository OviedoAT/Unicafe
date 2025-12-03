package com.example.unicafe.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    private lateinit var recyclerAlimentos: RecyclerView
    private lateinit var adapterAlimentos: ProductoAdapter

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

        presentador = ProductosPresentador(this)

        btnComprasAlimen = findViewById(R.id.btnComprasAlimen)
        btnMenuAlimen = findViewById(R.id.btnMenuAlimen)

        btnCombos = findViewById(R.id.btnCombos)
        btnPromos = findViewById(R.id.btnPromos)
        btnTrending = findViewById(R.id.btnTrending)
        btnComidaCal = findViewById(R.id.btnComidaCal)

        recyclerAlimentos = findViewById(R.id.recyclerCombos)
        recyclerAlimentos.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapterAlimentos = ProductoAdapter(emptyList()) { producto ->
            irADetalles(producto)
        }
        recyclerAlimentos.adapter = adapterAlimentos

        btnMenuAlimen.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Menú")
                .setItems(arrayOf("Seguir comprando", "Cerrar sesión")) { _, option ->
                    if (option == 1) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
                .show()
        }

        btnComprasAlimen.setOnClickListener {
            Toast.makeText(this, "Carrito de compras aún no implementado", Toast.LENGTH_SHORT)
                .show()
        }

        configurarBotonesCategoria()

        presentador.cargarProductos()
    }

    private fun configurarBotonesCategoria() {

        btnCombos.setOnClickListener {
            presentador.cargarProductosPorCategoria("combos")
        }

        btnPromos.setOnClickListener {
            presentador.cargarProductosPorCategoria("snacks")
        }

        btnTrending.setOnClickListener {
            presentador.cargarProductosPorCategoria("Postres")
        }

        btnComidaCal.setOnClickListener {
            presentador.cargarProductosPorCategoria("Comidas")
        }
    }


    override fun mostrarCargando(mostrar: Boolean) {
    }

    override fun mostrarProductos(lista: List<Producto>) {
        adapterAlimentos.actualizarDatos(lista)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presentador.onDestroy()
    }


    private fun irADetalles(producto: Producto) {
        val intent = Intent(this, DetallesAlimentos::class.java).apply {
            putExtra("idProducto", producto.idProducto)
            putExtra("nombre", producto.nombre)
            putExtra("descripcion", producto.descripcion)
            putExtra("precio", producto.precio)
            putExtra("url_imagen", producto.url_imagen)
        }
        startActivity(intent)
    }
}