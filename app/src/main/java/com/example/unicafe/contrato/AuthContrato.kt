package com.example.unicafe.contrato

import com.example.unicafe.modelo.dto.LoginResponse

interface AuthContrato {
    interface Vista {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarLoginExitoso(usuario: LoginResponse)
        fun mostrarErrorLogin(mensaje: String)
        fun mostrarRegistroExitoso(mensaje: String)
        fun mostrarErrorRegistro(mensaje: String)
    }

    interface Presentador {
        fun login(correo: String, password: String)
        fun registrarUsuario(
            nombre: String,
            aPaterno: String,
            aMaterno: String,
            correo: String,
            telefono: String,
            password: String,
            idPerfil: Int = 1 // Alumno por defecto
        )

        fun onDestroy()
    }

    interface Model {
        fun login(
            correo: String,
            password: String,
            callback: (Boolean, LoginResponse?, String?) -> Unit
        )

        fun registrarUsuario(
            nombre: String,
            aPaterno: String,
            aMaterno: String,
            correo: String,
            telefono: String,
            password: String,
            idPerfil: Int,
            callback: (Boolean, String?) -> Unit
        )
    }
}