package com.example.unicafe.modelo.dto

data class Producto(
    val id_producto: Int,
    val nombre: String,
    val stock: Int,
    val estado: String,
    val categoria: Int,
    val precio: Double,
    val descripcion: String,
    val url_imagen: String
)
