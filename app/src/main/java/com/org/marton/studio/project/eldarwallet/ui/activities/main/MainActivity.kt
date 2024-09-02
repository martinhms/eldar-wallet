package com.org.marton.studio.project.eldarwallet.ui.activities.main

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.main.adapter.DigitalCardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addCardButton: FloatingActionButton = findViewById(R.id.agregarTarjetaButton)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val balanceTextView: TextView = findViewById(R.id.balanceTextView)
        val usernameTextView: TextView = findViewById(R.id.usernameEditText)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.qr_paid_tab -> {
                    true
                }

                R.id.contacless_paid_tab -> {
                    true
                }

                R.id.main_activity_tab -> {
                    true
                }

                else -> false
            }
        }
        viewModel.userData.observe(this) { userData ->
            // Actualizar la UI con los datos del usuario
            usernameTextView.text = getGreattings(userData.userName + " " + userData.userLastname)
            balanceTextView.text = String.format("$ %.2f", userData.balance)

            val recyclerView: RecyclerView = findViewById(R.id.tarjetasRecyclerView)
            val adapter = DigitalCardAdapter(userData.cards?: emptyList())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        addCardButton.setOnClickListener {
            //TODO
        }
    }

    private fun getGreattings(s: String): String {
        return "Hi $s!"
    }
}
