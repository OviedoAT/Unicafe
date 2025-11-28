package com.example.unicafe.modelo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // ⚠️ CAMBIA ESTA URL por la de tu hosting real
    // OJO: Debe terminar en "/" y apuntar a la carpeta de las APIs
    private const val BASE_URL = "https://TU_DOMINIO.com/unicafe/api_movil/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}