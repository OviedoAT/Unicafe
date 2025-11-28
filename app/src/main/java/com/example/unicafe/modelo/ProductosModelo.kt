package com.example.unicafe.modelo

import com.example.unicafe.contrato.ProductosContrato
import com.example.unicafe.modelo.dto.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductosModelo (private val api: ApiService = ApiClient.apiService) : ProductosContrato.Model {
    override fun obtenerProductos(
        callback: (Boolean, List<Producto>?, String?) -> Unit
    ) {
        api.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                if (response.isSuccessful) {
                    val lista = response.body()
                    if (lista != null) {
                        callback(true, lista, null)
                    } else {
                        callback(false, null, "Respuesta vacía")
                    }
                } else {
                    callback(false, null, "Error de servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                callback(false, null, "Error de conexión: ${t.localizedMessage}")
            }
        })
    }
}