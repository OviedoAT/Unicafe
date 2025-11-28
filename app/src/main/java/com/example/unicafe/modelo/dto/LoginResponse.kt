package com.example.unicafe.modelo.dto

data class LoginResponse(
    val success: Boolean,
    val idUsuario: Int?,
    val nombreCompleto: String?,
    val correo: String?,
    val telefono: String?,
    val idPerfil: Int?,
    val perfil: String?,
    val message: String? = null
)
