package com.example.unicafe.modelo

data class RespuestaProductos(val success: Boolean,
                              val message: String,
                              val data: List<Producto>
)

data class Producto(
    @com.google.gson.annotations.SerializedName("idProducto") val idProducto: Int,
    @com.google.gson.annotations.SerializedName("nombre") val nombre: String,
    @com.google.gson.annotations.SerializedName("descripcion") val descripcion: String,
    @com.google.gson.annotations.SerializedName("precio") val precio: Double,
    @com.google.gson.annotations.SerializedName("categoria") val categoria: String,
    @com.google.gson.annotations.SerializedName("url_imagen") val urlImagen: String?
)
