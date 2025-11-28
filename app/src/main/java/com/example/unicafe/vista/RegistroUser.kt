package com.example.unicafe.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unicafe.MainActivity
import com.example.unicafe.R
import com.example.unicafe.contrato.AuthContrato
import com.example.unicafe.modelo.ApiClient
import com.example.unicafe.modelo.dto.LoginResponse
import com.example.unicafe.modelo.dto.RegisterResponse
import com.example.unicafe.presentador.AuthPresentador

class RegistroUser : AppCompatActivity(), AuthContrato.Vista {

    // Views
    private lateinit var btnMenuRegis: Button
    private lateinit var txtboxNombre: EditText
    private lateinit var txtboxApePaterno: EditText
    private lateinit var txtboxApeMaterno: EditText
    private lateinit var txtboxUser: EditText
    private lateinit var txtboxEmail: EditText
    private lateinit var txtboxTel: EditText
    private lateinit var txtboxPass: EditText
    private lateinit var btnCleanRegistrar: Button
    private lateinit var btnGuardar: Button

    // MVP
    private lateinit var presentador: AuthContrato.Presentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_user)

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Presentador
        presentador = AuthPresentador(this)

        // Referencias a vistas
        btnMenuRegis        = findViewById(R.id.btnMenuRegis)
        txtboxNombre        = findViewById(R.id.txtboxNombre)
        txtboxApePaterno    = findViewById(R.id.txtboxApePaterno)
        txtboxApeMaterno    = findViewById(R.id.txtboxApeMaterno)
        txtboxUser          = findViewById(R.id.txtboxUser)
        txtboxEmail         = findViewById(R.id.txtboxEmail)
        txtboxTel           = findViewById(R.id.txtboxTel)
        txtboxPass          = findViewById(R.id.txtboxPass)
        btnCleanRegistrar   = findViewById(R.id.btnCleanRegistrar)
        btnGuardar          = findViewById(R.id.btnGuardar)

        // Botón menú: regresar al login
        btnMenuRegis.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Regresar")
                .setMessage("¿Deseas regresar al inicio de sesión?")
                .setPositiveButton("Sí") { _, _ ->
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }

        // Limpiar campos
        btnCleanRegistrar.setOnClickListener {
            txtboxNombre.text.clear()
            txtboxApePaterno.text.clear()
            txtboxApeMaterno.text.clear()
            txtboxUser.text.clear()
            txtboxEmail.text.clear()
            txtboxTel.text.clear()
            txtboxPass.text.clear()
        }

        // Guardar / Registrar usuario
        btnGuardar.setOnClickListener {
            val nombre   = txtboxNombre.text.toString().trim()
            val aPat     = txtboxApePaterno.text.toString().trim()
            val aMat     = txtboxApeMaterno.text.toString().trim()
            val correo   = txtboxEmail.text.toString().trim()
            val tel      = txtboxTel.text.toString().trim()
            val pass     = txtboxPass.text.toString().trim()
            val usuario  = txtboxUser.text.toString().trim()  // si luego lo quieres guardar

            if (nombre.isEmpty() || aPat.isEmpty() || correo.isEmpty()
                || tel.isEmpty() || pass.isEmpty()
            ) {
                Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // idPerfil = 2 (cliente, por ejemplo)
            val call = ApiClient.apiService.registrarUsuario(
                nombre   = nombre,
                aPaterno = aPat,
                aMaterno = aMat,
                correo   = correo,
                telefono = tel,
                password = pass,
                idPerfil = 2
            )

            // Deshabilitar botón mientras se hace la petición (opcional)
            btnGuardar.isEnabled = false

            call.enqueue(object : retrofit2.Callback<RegisterResponse> {
                override fun onResponse(
                    call: retrofit2.Call<RegisterResponse>,
                    response: retrofit2.Response<RegisterResponse>
                ) {
                    btnGuardar.isEnabled = true

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.success) {
                            Toast.makeText(
                                this@RegistroUser,
                                body.message,
                                Toast.LENGTH_LONG
                            ).show()

                            // Opcional: regresar al login
                            val intent = Intent(this@RegistroUser, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(
                                this@RegistroUser,
                                body?.message ?: "Error al registrar.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegistroUser,
                            "Error en el servidor (${response.code()})",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
                    btnGuardar.isEnabled = true
                    Toast.makeText(
                        this@RegistroUser,
                        "Error de conexión: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    // ===================== AuthContrato.Vista =====================

    override fun mostrarCargando(mostrar: Boolean) {
        btnGuardar.isEnabled = !mostrar
        btnCleanRegistrar.isEnabled = !mostrar
        btnMenuRegis.isEnabled = !mostrar
    }

    override fun mostrarRegistroExitoso(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Registro exitoso")
            .setMessage(mensaje)
            .setPositiveButton("Ir al inicio de sesión") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Seguir aquí", null)
            .show()
    }

    override fun mostrarErrorRegistro(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarLoginExitoso(usuario: LoginResponse) {
        // No se usa en esta pantalla
    }

    override fun mostrarErrorLogin(mensaje: String) {
        // No se usa en esta pantalla
    }

    override fun onDestroy() {
        presentador.onDestroy()
        super.onDestroy()
    }
}