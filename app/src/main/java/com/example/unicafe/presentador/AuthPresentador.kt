package com.example.unicafe.presentador

import com.example.unicafe.contrato.AuthContrato
import com.example.unicafe.modelo.AuthModelo

class AuthPresentador(
    private var vista: AuthContrato.Vista?,
    private val modelo: AuthContrato.Model = AuthModelo()
) : AuthContrato.Presentador {

    override fun login(correo: String, password: String) {
        vista?.mostrarCargando(true)
        modelo.login(correo, password) { ok, user, error ->
            vista?.mostrarCargando(false)
            if (ok && user != null) {
                vista?.mostrarLoginExitoso(user)
            } else {
                vista?.mostrarErrorLogin(error ?: "Error desconocido")
            }
        }
    }

    override fun registrarUsuario(
        nombre: String,
        aPaterno: String,
        aMaterno: String,
        correo: String,
        telefono: String,
        password: String,
        idPerfil: Int
    ) {
        vista?.mostrarCargando(true)
        modelo.registrarUsuario(nombre, aPaterno, aMaterno, correo, telefono, password, idPerfil) { ok, msg ->
            vista?.mostrarCargando(false)
            if (ok) {
                vista?.mostrarRegistroExitoso(msg ?: "Usuario registrado correctamente")
            } else {
                vista?.mostrarErrorRegistro(msg ?: "Error al registrar usuario")
            }
        }
    }

    override fun onDestroy() {
        vista = null
    }
}