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
import com.example.unicafe.presentador.AuthPresentador
import com.example.unicafe.modelo.dto.LoginResponse

class RegistroUser : AppCompatActivity(), AuthContrato.Vista {

    private lateinit var btnMenuRegis: Button
    private lateinit var txtNombre: EditText
    private lateinit var txtApePaterno: EditText
    private lateinit var txtApeMaterno: EditText
    private lateinit var txtUser: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtTel: EditText
    private lateinit var txtPass: EditText
    private lateinit var btnCleanRegistrar: Button
    private lateinit var btnGuardar: Button

    private lateinit var presentador: AuthPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_user)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, ins ->
            val s = ins.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(s.left, s.top, s.right, s.bottom)
            ins
        }

        btnMenuRegis = findViewById(R.id.btnMenuRegis)
        txtNombre = findViewById(R.id.txtboxNombre)
        txtApePaterno = findViewById(R.id.txtboxApePaterno)
        txtApeMaterno = findViewById(R.id.txtboxApeMaterno)
        txtUser = findViewById(R.id.txtboxUser)
        txtEmail = findViewById(R.id.txtboxEmail)
        txtTel = findViewById(R.id.txtboxTel)
        txtPass = findViewById(R.id.txtboxPass)
        btnCleanRegistrar = findViewById(R.id.btnCleanRegistrar)
        btnGuardar = findViewById(R.id.btnGuardar)

        presentador = AuthPresentador(this)

        btnMenuRegis.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnCleanRegistrar.setOnClickListener {
            txtNombre.text.clear()
            txtApePaterno.text.clear()
            txtApeMaterno.text.clear()
            txtUser.text.clear()
            txtEmail.text.clear()
            txtTel.text.clear()
            txtPass.text.clear()
        }

        btnGuardar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val aPat = txtApePaterno.text.toString()
            val aMat = txtApeMaterno.text.toString()
            val email = txtEmail.text.toString()
            val tel = txtTel.text.toString()
            val pass = txtPass.text.toString()

            if (nombre.isEmpty() || aPat.isEmpty() || email.isEmpty() ||
                tel.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            presentador.registrarUsuario(
                nombre, aPat, aMat, email, tel, pass, 2
            )
        }
    }

    override fun mostrarRegistroExitoso(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Registro exitoso")
            .setMessage(mensaje)
            .setPositiveButton("Continuar") { _, _ ->
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .show()
    }

    override fun mostrarErrorRegistro(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun mostrarLoginExitoso(usuario: LoginResponse) {}
    override fun mostrarErrorLogin(mensaje: String) {}

    override fun mostrarCargando(mostrar: Boolean) {
        btnGuardar.isEnabled = !mostrar
        btnCleanRegistrar.isEnabled = !mostrar
    }
}