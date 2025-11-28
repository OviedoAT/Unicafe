package com.example.unicafe


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unicafe.contrato.AuthContrato
import com.example.unicafe.modelo.dto.LoginResponse
import com.example.unicafe.presentador.AuthPresentador
import com.example.unicafe.vista.Alimentos
import com.example.unicafe.vista.RegistroUser

class MainActivity : AppCompatActivity(), AuthContrato.Vista {

    private lateinit var layoutBlurred: LinearLayout
    private lateinit var txtUsuario: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnInicioSesion: Button
    private lateinit var btnIrRegistro: Button

    private lateinit var presentador: AuthContrato.Presentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajuste de insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // MVP
        presentador = AuthPresentador(this)

        // Referencias a vistas
        layoutBlurred = findViewById(R.id.layoutBlurred)
        txtUsuario = findViewById(R.id.txtboxUsuario)
        txtPassword = findViewById(R.id.txtboxUserContra)
        btnInicioSesion = findViewById(R.id.btnInicioSesion)
        btnIrRegistro = findViewById(R.id.btnIrRegistro)

        // Login
        btnInicioSesion.setOnClickListener {
            val correo = txtUsuario.text.toString().trim()
            val pass = txtPassword.text.toString().trim()

            if (correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            presentador.login(correo, pass)
        }

        // Ir a registro
        btnIrRegistro.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistroUser::class.java)
            startActivity(intent)
        }
    }

    // === Implementación de AuthContrato.Vista ===

    override fun mostrarCargando(mostrar: Boolean) {
        btnInicioSesion.isEnabled = !mostrar
        btnIrRegistro.isEnabled = !mostrar
    }

    override fun mostrarLoginExitoso(usuario: LoginResponse) {
        Toast.makeText(
            this,
            "Bienvenido ${usuario.nombreCompleto ?: ""}",
            Toast.LENGTH_SHORT
        ).show()

        val intent = Intent(this@MainActivity, Alimentos::class.java)
        // Si quieres pasar datos del usuario:
        intent.putExtra("ID_USUARIO", usuario.idUsuario ?: -1)
        startActivity(intent)
        finish() // para que no puedan volver al login con back
    }

    override fun mostrarErrorLogin(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarRegistroExitoso(mensaje: String) {
        // No se usa en esta pantalla
    }

    override fun mostrarErrorRegistro(mensaje: String) {
        // No se usa en esta pantalla
    }

    override fun onDestroy() {
        presentador.onDestroy()
        super.onDestroy()
    }
}