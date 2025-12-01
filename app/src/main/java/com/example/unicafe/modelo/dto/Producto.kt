package com.example.unicafe.modelo.dto

data class Producto(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val url_imagen: String?
)

