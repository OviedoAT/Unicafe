package com.example.unicafe.modelo.dto

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val idUsuario: Int?,
    val nombreCompleto: String?
)
