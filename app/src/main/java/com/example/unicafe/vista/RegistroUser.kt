package com.example.unicafe.vista

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unicafe.R
import com.example.unicafe.contrato.AuthContrato
import com.example.unicafe.modelo.dto.LoginResponse
import com.example.unicafe.presentador.AuthPresentador

class RegistroUser : AppCompatActivity(), AuthContrato.Vista {

    private lateinit var btnMenuRegis: ImageButton
    private lateinit var txtboxNombre: EditText
    private lateinit var txtboxApePaterno: EditText
    private lateinit var txtboxApeMaterno: EditText
    private lateinit var txtboxUser: EditText
    private lateinit var txtboxEmail: EditText
    private lateinit var txtboxTel: EditText
    private lateinit var txtboxPass: EditText
    private lateinit var btnCleanRegistrar: Button
    private lateinit var btnGuardar: Button

    private lateinit var presentador: AuthPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias
        btnMenuRegis = findViewById(R.id.btnMenuRegis)
        txtboxNombre = findViewById(R.id.txtboxNombre)
        txtboxApePaterno = findViewById(R.id.txtboxApePaterno)
        txtboxApeMaterno = findViewById(R.id.txtboxApeMaterno)
        txtboxUser = findViewById(R.id.txtboxUser)
        txtboxEmail = findViewById(R.id.txtboxEmail)
        txtboxTel = findViewById(R.id.txtboxTel)
        txtboxPass = findViewById(R.id.txtboxPass)
        btnCleanRegistrar = findViewById(R.id.btnCleanRegistrar)
        btnGuardar = findViewById(R.id.btnGuardar)

        presentador = AuthPresentador(this)

        btnMenuRegis.setOnClickListener {
            finish() // vuelve al login
        }

        btnCleanRegistrar.setOnClickListener {
            limpiarCampos()
        }

        btnGuardar.setOnClickListener {
            val nombre = txtboxNombre.text.toString().trim()
            val aPaterno = txtboxApePaterno.text.toString().trim()
            val aMaterno = txtboxApeMaterno.text.toString().trim()
            val correo = txtboxEmail.text.toString().trim()
            val telefono = txtboxTel.text.toString().trim()
            val password = txtboxPass.text.toString().trim()
            // txtboxUser lo puedes usar solo para mostrar en la interfaz si quieres

            if (nombre.isEmpty() || aPaterno.isEmpty() || correo.isEmpty()
                || telefono.isEmpty() || password.isEmpty()
            ) {
                Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                // idPerfil = 1 (Alumno por defecto). Cámbialo si quieres otro perfil.
                presentador.registrarUsuario(
                    nombre,
                    aPaterno,
                    aMaterno,
                    correo,
                    telefono,
                    password,
                    idPerfil = 1
                )
            }
        }
    }

    private fun limpiarCampos() {
        txtboxNombre.text.clear()
        txtboxApePaterno.text.clear()
        txtboxApeMaterno.text.clear()
        txtboxUser.text.clear()
        txtboxEmail.text.clear()
        txtboxTel.text.clear()
        txtboxPass.text.clear()
    }

    // ===== Implementación AuthContrato.Vista =====

    override fun mostrarCargando(mostrar: Boolean) {
        btnGuardar.isEnabled = !mostrar
        btnCleanRegistrar.isEnabled = !mostrar
        btnMenuRegis.isEnabled = !mostrar
    }

    override fun mostrarRegistroExitoso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        limpiarCampos()
        finish() // regresa al login
    }

    override fun mostrarErrorRegistro(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    // No se usan en esta pantalla:
    override fun mostrarLoginExitoso(usuario: LoginResponse) {}
    override fun mostrarErrorLogin(mensaje: String) {}

    override fun onDestroy() {
        presentador.onDestroy()
        super.onDestroy()
    }
}