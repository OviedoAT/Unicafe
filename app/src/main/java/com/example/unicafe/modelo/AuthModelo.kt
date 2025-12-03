package com.example.unicafe.modelo

import com.example.unicafe.api.ApiService
import com.example.unicafe.contrato.AuthContrato
import com.example.unicafe.modelo.dto.LoginResponse
import com.example.unicafe.modelo.dto.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthModelo(
    private val api: ApiService = ApiClient.apiService
) : AuthContrato.Model {

    override fun login(
        correo: String,
        password: String,
        callback: (Boolean, LoginResponse?, String?) -> Unit
    ) {
        api.login(correo, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        callback(true, body, null)
                    } else {
                        callback(false, null, body?.message ?: "Credenciales incorrectas")
                    }
                } else {
                    callback(false, null, "Error de servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(false, null, "Error de conexión: ${t.localizedMessage}")
            }
        })
    }

    override fun registrarUsuario(
        nombre: String,
        aPaterno: String,
        aMaterno: String,
        correo: String,
        telefono: String,
        password: String,
        idPerfil: Int,
        callback: (Boolean, String?) -> Unit
    ) {
        api.registrarUsuario(
            nombre,
            aPaterno,
            aMaterno,
            correo,
            telefono,
            password,
            idPerfil
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        callback(true, body.message)
                    } else {
                        callback(false, body?.message ?: "No se pudo registrar el usuario")
                    }
                } else {
                    callback(false, "Error de servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                callback(false, "Error de conexión: ${t.localizedMessage}")
            }
        })
    }
}