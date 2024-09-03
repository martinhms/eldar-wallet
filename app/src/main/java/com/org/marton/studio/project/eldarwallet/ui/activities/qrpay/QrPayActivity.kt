package com.org.marton.studio.project.eldarwallet.ui.activities.qrpay

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.main.MainActivity
import com.org.marton.studio.project.eldarwallet.ui.activities.main.adapter.DigitalCardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrPayActivity : AppCompatActivity() {

    private val viewModel: QrPayViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_qr_pay)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        viewModel.userData.observe(this) { userData ->
            val recyclerView: RecyclerView = findViewById(R.id.selectCardsPayRV)
            val adapter = DigitalCardAdapter(userData.cards ?: emptyList())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.qr_paid_tab -> {
                    intent = Intent(this, QrPayActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.main_activity_tab -> {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.contacless_paid_tab -> {
                    true
                }


                else -> false
            }
        }
    }
}