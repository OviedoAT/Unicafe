package com.example.unicafe.vista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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

    private lateinit var btnMenuAlimen: Button
    private lateinit var btnCombos: Button
    private lateinit var btnPromos: Button
    private lateinit var btnTrending: Button
    private lateinit var btnComidaCal: Button
    private lateinit var btnComprasAlimen: Button

    private lateinit var recyclerCombos: RecyclerView
    private lateinit var recyclerPromos: RecyclerView
    private lateinit var recyclerTrending: RecyclerView

    // Agrega un ProgressBar al layout si no existe
    private var progressBar: ProgressBar? = null

    private lateinit var adapterCombos: ProductoAdapter
    private lateinit var adapterPromos: ProductoAdapter
    private lateinit var adapterTrending: ProductoAdapter

    private lateinit var presentador: ProductosContrato.Presentador

    companion object {
        private const val TAG = "Alimentos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            enableEdgeToEdge()
            setContentView(R.layout.activity_alimentos)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Inicializar presentador
            presentador = ProductosPresentador(this)

            // Inicializar vistas
            inicializarVistas()

            // Configurar RecyclerViews
            configurarRecyclerViews()

            // Configurar listeners
            configurarListeners()

            // Cargar productos desde la API
            Log.d(TAG, "Iniciando carga de productos...")
            presentador.cargarProductos()

        } catch (e: Exception) {
            Log.e(TAG, "Error en onCreate: ${e.message}", e)
            Toast.makeText(this, "Error al inicializar la pantalla: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun inicializarVistas() {
        btnMenuAlimen = findViewById(R.id.btnMenuAlimen)
        btnCombos = findViewById(R.id.btnCombos)
        btnPromos = findViewById(R.id.btnPromos)
        btnTrending = findViewById(R.id.btnTrending)
        btnComidaCal = findViewById(R.id.btnComidaCal)
        btnComprasAlimen = findViewById(R.id.btnComprasAlimen)

        recyclerCombos = findViewById(R.id.recyclerCombos)
        recyclerPromos = findViewById(R.id.recyclerPromos)
        recyclerTrending = findViewById(R.id.recyclerTrending)
    }

    private fun configurarRecyclerViews() {
        // Configurar adaptadores con listener de clic
        adapterCombos = ProductoAdapter(emptyList()) { producto ->
            abrirDetallesProducto(producto)
        }
        adapterPromos = ProductoAdapter(emptyList()) { producto ->
            abrirDetallesProducto(producto)
        }
        adapterTrending = ProductoAdapter(emptyList()) { producto ->
            abrirDetallesProducto(producto)
        }

        // Configurar layout managers
        recyclerCombos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerPromos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerTrending.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Asignar adaptadores
        recyclerCombos.adapter = adapterCombos
        recyclerPromos.adapter = adapterPromos
        recyclerTrending.adapter = adapterTrending
    }

    private fun configurarListeners() {
        // Menú
        btnMenuAlimen.setOnClickListener {
            mostrarMenuOpciones()
        }

        // Botones de filtros
        btnCombos.setOnClickListener {
            Toast.makeText(this, "Filtrar por combos (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnPromos.setOnClickListener {
            Toast.makeText(this, "Filtrar por promos (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnTrending.setOnClickListener {
            Toast.makeText(this, "Filtrar por trending (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnComidaCal.setOnClickListener {
            Toast.makeText(this, "Filtrar por comida caliente (pendiente)", Toast.LENGTH_SHORT).show()
        }

        btnComprasAlimen.setOnClickListener {
            Toast.makeText(this, "Carrito de compras (pendiente)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarMenuOpciones() {
        AlertDialog.Builder(this)
            .setTitle("Menú")
            .setItems(arrayOf("Seguir comprando", "Cerrar sesión")) { _, which ->
                when (which) {
                    0 -> {
                        // Nada, solo cerrar el diálogo
                    }
                    1 -> {
                        cerrarSesion()
                    }
                }
            }
            .show()
    }

    private fun cerrarSesion() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun abrirDetallesProducto(producto: Producto) {
        try {
            val intent = Intent(this, DetallesAlimentos::class.java).apply {
                // Pasar los datos del producto
                putExtra("PRODUCTO_NOMBRE", producto.nombre)
                putExtra("PRODUCTO_DESCRIPCION", producto.descripcion)
                putExtra("PRODUCTO_PRECIO", producto.precio)
                // Agrega otros campos según tu modelo Producto
            }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error al abrir detalles: ${e.message}", e)
            Toast.makeText(this, "Error al abrir los detalles del producto", Toast.LENGTH_SHORT).show()
        }
    }

    // === ProductosContrato.Vista ===

    override fun mostrarCargando(mostrar: Boolean) {
        Log.d(TAG, "mostrarCargando: $mostrar")
        progressBar?.visibility = if (mostrar) View.VISIBLE else View.GONE

        // Opcional: deshabilitar interacciones mientras carga
        val alpha = if (mostrar) 0.5f else 1.0f
        recyclerCombos.alpha = alpha
        recyclerPromos.alpha = alpha
        recyclerTrending.alpha = alpha
    }

    override fun mostrarProductos(lista: List<Producto>) {
        Log.d(TAG, "mostrarProductos: ${lista.size} productos recibidos")

        if (lista.isEmpty()) {
            Toast.makeText(this, "No hay productos disponibles", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Puedes filtrar o separar los productos por categoría aquí
            // Por ahora mostramos la misma lista en las 3 secciones
            adapterCombos.actualizarDatos(lista)
            adapterPromos.actualizarDatos(lista)
            adapterTrending.actualizarDatos(lista)

            Toast.makeText(this, "Productos cargados exitosamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Error al mostrar productos: ${e.message}", e)
            Toast.makeText(this, "Error al mostrar productos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun mostrarError(mensaje: String) {
        Log.e(TAG, "Error: $mensaje")
        Toast.makeText(this, "Error: $mensaje", Toast.LENGTH_LONG).show()

        // Opcional: mostrar un diálogo con opción de reintentar
        AlertDialog.Builder(this)
            .setTitle("Error al cargar productos")
            .setMessage(mensaje)
            .setPositiveButton("Reintentar") { _, _ ->
                presentador.cargarProductos()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        try {
            presentador.onDestroy()
        } catch (e: Exception) {
            Log.e(TAG, "Error en onDestroy: ${e.message}", e)
        }
        super.onDestroy()
    }
}