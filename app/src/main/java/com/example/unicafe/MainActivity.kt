package com.example.unicafe

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
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



class MainActivity : AppCompatActivity(), AuthContrato.Vista{
    // Layout borroso del fondo
    private lateinit var layoutBorroso: LinearLayout

    // Campos de login
    private lateinit var txtboxUsuario: EditText
    private lateinit var txtboxUserContra: EditText
    private lateinit var btnInicioSesion: Button
    private lateinit var btnIrRegistro: Button

    // Presenter (lógica de login con Retrofit)
    private lateinit var presentador: AuthPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
// ---- Referencias a vistas ----
        layoutBorroso = findViewById(R.id.layoutBlurred)

        txtboxUsuario = findViewById(R.id.txtboxUsuario)       // correo
        txtboxUserContra = findViewById(R.id.txtboxUserContra) // password
        btnInicioSesion = findViewById(R.id.btnInicioSesion)
        btnIrRegistro = findViewById(R.id.btnIrRegistro)

        // ---- Efecto de blur en el layout de fondo (Android 12+) ----
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val efecto = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
            layoutBorroso.setRenderEffect(efecto)
        }

        // ---- Inicializar presenter ----
        presentador = AuthPresentador(this)

        // ---- Eventos ----
        btnInicioSesion.setOnClickListener {
            val correo = txtboxUsuario.text.toString().trim()
            val password = txtboxUserContra.text.toString().trim()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí se dispara Retrofit a través del modelo → API PHP
                presentador.login(correo, password)
            }
        }

        btnIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroUser::class.java))
        }
    }

    // ========== Implementación de AuthContrato.Vista ==========

    override fun mostrarCargando(mostrar: Boolean) {
        // Desactivar botones mientras se hace la petición al servidor
        btnInicioSesion.isEnabled = !mostrar
        btnIrRegistro.isEnabled = !mostrar
    }

    override fun mostrarLoginExitoso(usuario: LoginResponse) {
        // Login correcto → ir a la pantalla de productos (Alimentos)
        Toast.makeText(this, "Bienvenido ${usuario.nombreCompleto}", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, Alimentos::class.java)
        intent.putExtra("NOMBRE_COMPLETO", usuario.nombreCompleto)
        intent.putExtra("PERFIL", usuario.perfil)
        startActivity(intent)
        finish()
    }

    override fun mostrarErrorLogin(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    // Estos dos no se usan en MainActivity (solo en RegistroUser)
    override fun mostrarRegistroExitoso(mensaje: String) {
        // no aplica aquí
    }

    override fun mostrarErrorRegistro(mensaje: String) {
        // no aplica aquí
    }

    override fun onDestroy() {
        presentador.onDestroy()
        super.onDestroy()
    }
}