package com.example.unicafe.modelo

import com.example.unicafe.modelo.dto.LoginResponse
import com.example.unicafe.modelo.dto.RegisterResponse
import com.example.unicafe.modelo.dto.Producto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("api_login.php")
    fun login(
        @Field("correo") correo: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api_registro.php")
    fun registrarUsuario(
        @Field("nombre") nombre: String,
        @Field("aPaterno") aPaterno: String,
        @Field("aMaterno") aMaterno: String,
        @Field("correo") correo: String,
        @Field("telefono") telefono: String,
        @Field("password") password: String,
        @Field("idPerfil") idPerfil: Int
    ): Call<RegisterResponse>

    @GET("api_listar_productos.php")
    fun obtenerProductos(): Call<List<Producto>>
}