package com.org.marton.studio.project.eldarwallet.ui.activities.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SessionData.isUserLoggedIn()) {
            Intent(this, MainActivity::class.java)
                .also {
                    startActivity(it)
                    finish()
                }
        }
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val usernameLoginEditText: EditText = findViewById(R.id.usernameLoginEditText)
        val passwordLoginEditText: EditText = findViewById(R.id.passwordLoginEditText)
        val signInLoginButton: Button = findViewById(R.id.signInLoginButton)
        val signupLoginButton: Button = findViewById(R.id.signupLoginButton)

        val usernameFormEditText: EditText = findViewById(R.id.usernameFormEditText)
        val lastnameFormEditText: EditText = findViewById(R.id.lastnameFormEditText)
        val passwordFormEditText: EditText = findViewById(R.id.passwordFormEditText)
        val repasswordFormEditText: EditText = findViewById(R.id.repasswordFormEditText)
        val emailFormEditText: EditText = findViewById(R.id.emailFormEditText)
        val identificationFormEditText: EditText = findViewById(R.id.identificationFormEditText)
        val confirmFormButton: Button = findViewById(R.id.confirmFormButton)
        val cancelFormButton: Button = findViewById(R.id.cancelFormButton)

        setVisibilityObsercer(
            usernameEditText = usernameLoginEditText,
            passwordEditText = passwordLoginEditText,
            signInButton = signInLoginButton,
            signupButton = signupLoginButton,
            usernameFormEditText = usernameFormEditText,
            lastnameFormEditText = lastnameFormEditText,
            passwordFormEditText = passwordFormEditText,
            repasswordFormEditText = repasswordFormEditText,
            emailFormEditText = emailFormEditText,
            identificationFormEditText = identificationFormEditText,
            confirmFormButton = confirmFormButton,
            cancelFormButton = cancelFormButton
        )

        signupLoginButton.setOnClickListener {
            viewModel.toggleSignUp()
        }

        cancelFormButton.setOnClickListener {
            viewModel.toggleSignUp()
        }

        signInLoginButton.setOnClickListener {
            viewModel.getSingIn(
                usernameLoginEditText.text.toString(),
                passwordLoginEditText.text.toString()
            )
        }

        viewModel.loginState.observe(this) { loginSuccessful ->
            if (loginSuccessful) {
                Intent(this, MainActivity::class.java)
                    .also {
                        startActivity(it)
                        finish()
                    }
            } else {
                Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        confirmFormButton.setOnClickListener {
            if (validatesForm(
                    usernameFormEditText.text.toString(),
                    passwordFormEditText.text.toString(),
                    repasswordFormEditText.text.toString()
                )
            ) {
                viewModel.createUser(
                    UserData(
                        userName = usernameFormEditText.text.toString(),
                        userLastname = lastnameFormEditText.text.toString(),
                        password = passwordFormEditText.text.toString(),
                        identification = identificationFormEditText.text.toString(),
                        email = emailFormEditText.text.toString(),
                    )
                )
                viewModel.toggleSignUp()
            } else {
                Toast.makeText(this, "Something is incomplete", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validatesForm(
        usernameFormEditText: String,
        passwordFormEditText: String,
        repasswordFormEditText: String
    ): Boolean {
        if (usernameFormEditText.isNotEmpty() &&
            passwordFormEditText.isNotEmpty() &&
            repasswordFormEditText.isNotEmpty()
        ) {
            return passwordFormEditText == repasswordFormEditText
        } else {
            return false
        }
    }

    private fun setVisibilityObsercer(
        usernameEditText: EditText,
        passwordEditText: EditText,
        signInButton: Button,
        signupButton: Button,
        usernameFormEditText: EditText,
        lastnameFormEditText: EditText,
        passwordFormEditText: EditText,
        repasswordFormEditText: EditText,
        emailFormEditText: EditText,
        identificationFormEditText: EditText,
        confirmFormButton: Button,
        cancelFormButton: Button
    ) {
        viewModel.isSingnUp.observe(this) {
            if (it) {
                usernameFormEditText.visibility = View.VISIBLE
                lastnameFormEditText.visibility = View.VISIBLE
                passwordFormEditText.visibility = View.VISIBLE
                repasswordFormEditText.visibility = View.VISIBLE
                emailFormEditText.visibility = View.VISIBLE
                identificationFormEditText.visibility = View.VISIBLE
                confirmFormButton.visibility = View.VISIBLE
                cancelFormButton.visibility = View.VISIBLE

                usernameEditText.visibility = View.GONE
                passwordEditText.visibility = View.GONE
                signInButton.visibility = View.GONE
                signupButton.visibility = View.GONE
            } else {
                usernameFormEditText.visibility = View.GONE
                lastnameFormEditText.visibility = View.GONE
                passwordFormEditText.visibility = View.GONE
                repasswordFormEditText.visibility = View.GONE
                emailFormEditText.visibility = View.GONE
                identificationFormEditText.visibility = View.GONE
                confirmFormButton.visibility = View.GONE
                cancelFormButton.visibility = View.GONE

                usernameEditText.visibility = View.VISIBLE
                passwordEditText.visibility = View.VISIBLE
                signInButton.visibility = View.VISIBLE
                signupButton.visibility = View.VISIBLE
            }
        }

    }

}




