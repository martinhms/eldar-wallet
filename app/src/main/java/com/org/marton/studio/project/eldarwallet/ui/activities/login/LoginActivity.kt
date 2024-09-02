package com.org.marton.studio.project.eldarwallet.ui.activities.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.main.MainActivity
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val singInButton: Button = findViewById(R.id.loginButton)

        singInButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val createUserButton: Button = findViewById(R.id.createUserButton)
        createUserButton.setOnClickListener {
            //TODO Crea un usuario test ELIMINAR
            val userData = UserData(
                id = 1234,
                userName = "Martin",
                userLastname = "Mendez",
                password = "1234",
                identification = "1234",
                email = "artin@gmail.com,",
                createdTime = System.currentTimeMillis()
            )
            viewModel.createUser(userData)
            Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show()
        }
    }
}