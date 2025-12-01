package com.example.unicafe.modelo.dto

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val idUsuario: Int?
)
