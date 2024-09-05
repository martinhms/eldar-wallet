package com.org.marton.studio.project.eldarwallet.ui.activities.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.se.omapi.Session
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.addcard.AddDigitalCardActivity
import com.org.marton.studio.project.eldarwallet.ui.activities.contactlesspay.ContactlessPayActivity
import com.org.marton.studio.project.eldarwallet.ui.activities.login.LoginActivity
import com.org.marton.studio.project.eldarwallet.ui.activities.main.adapter.DigitalCardAdapter
import com.org.marton.studio.project.eldarwallet.ui.activities.qrpay.QrPayActivity
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    companion object {
        var selectedItemId = R.id.main_activity_tab
    }
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
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val addCardButton: FloatingActionButton = findViewById(R.id.agregarTarjetaButton)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val balanceTextView: TextView = findViewById(R.id.balanceTextView)
        val usernameTextView: TextView = findViewById(R.id.usernameEditText)


        bottomNavigationView.setOnItemSelectedListener { item ->
            selectedItemId = item.itemId

            when (item.itemId) {
                R.id.qr_paid_tab -> {
                    intent = Intent(this, QrPayActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.main_activity_tab -> {
                    true
                }

                R.id.contacless_paid_tab -> {
                    intent = Intent(this, ContactlessPayActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }

        }
        bottomNavigationView.selectedItemId = selectedItemId

        viewModel.userData.observe(this) { userData ->
            usernameTextView.text = getGreattings(userData.userName + " " + userData.userLastname)
            balanceTextView.text = String.format("$ %.2f", userData.balance)

            val recyclerView: RecyclerView = findViewById(R.id.tarjetasRecyclerView)
            val adapter = DigitalCardAdapter(userData.cards ?: emptyList())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        addCardButton.setOnClickListener {
            intent = Intent(this, AddDigitalCardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_action -> {
                SessionData.logout()
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getGreattings(s: String): String {
        return "Hi $s!"
    }
}
