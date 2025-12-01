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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val s = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, s.bottom)
            insets
        }

        presentador = AuthPresentador(this)

        layoutBlurred = findViewById(R.id.layoutBlurred)
        txtUsuario = findViewById(R.id.txtboxUsuario)
        txtPassword = findViewById(R.id.txtboxUserContra)
        btnInicioSesion = findViewById(R.id.btnInicioSesion)
        btnIrRegistro = findViewById(R.id.btnIrRegistro)

        btnInicioSesion.setOnClickListener {
            val correo = txtUsuario.text.toString().trim()
            val pass = txtPassword.text.toString().trim()

            if (correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            presentador.login(correo, pass)
        }

        btnIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroUser::class.java))
        }
    }

    override fun mostrarCargando(mostrar: Boolean) {
        btnInicioSesion.isEnabled = !mostrar
        btnIrRegistro.isEnabled = !mostrar
    }

    override fun mostrarLoginExitoso(usuario: LoginResponse) {
        Toast.makeText(this, "Bienvenido ${usuario.nombreCompleto}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Alimentos::class.java)
        intent.putExtra("ID_USUARIO", usuario.idUsuario)
        startActivity(intent)
        finish()
    }

    override fun mostrarErrorLogin(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarRegistroExitoso(mensaje: String) {}
    override fun mostrarErrorRegistro(mensaje: String) {}

    override fun onDestroy() {
        presentador.onDestroy()
        super.onDestroy()
    }
}