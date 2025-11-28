package com.example.unicafe.presentador

import com.example.unicafe.contrato.ProductosContrato
import com.example.unicafe.modelo.ProductosModelo

class ProductosPresentador(
    private var vista: ProductosContrato.Vista?,
    private val modelo: ProductosContrato.Model = ProductosModelo()
) : ProductosContrato.Presentador {

    override fun cargarProductos() {
        vista?.mostrarCargando(true)
        modelo.obtenerProductos { ok, lista, error ->
            vista?.mostrarCargando(false)
            if (ok && lista != null) {
                vista?.mostrarProductos(lista)
            } else {
                vista?.mostrarError(error ?: "No se pudieron cargar los productos")
            }
        }
    }

    override fun onDestroy() {
        vista = null
    }
}