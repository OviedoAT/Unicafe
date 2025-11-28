package com.example.unicafe.contrato

import com.example.unicafe.modelo.dto.Producto

interface ProductosContrato {
    interface Vista {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarProductos(lista: List<Producto>)
        fun mostrarError(mensaje: String)
    }

    interface Presentador {
        fun cargarProductos()
        fun onDestroy()
    }

    interface Model {
        fun obtenerProductos(
            callback: (Boolean, List<Producto>?, String?) -> Unit
        )
    }
}