package com.org.marton.studio.project.eldarwallet.ui.activities.contactlesspay

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
import com.org.marton.studio.project.eldarwallet.ui.activities.qrpay.QrPayActivity
import com.org.marton.studio.project.eldarwallet.ui.activities.qrpay.adapter.OnCardClickListener
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactlessPayActivity : AppCompatActivity(), OnCardClickListener {

    private val viewModel: ContactlessPayViewModel by viewModels()
    private var selectedDigitalCard: DigitalCard? = null


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contactless_pay)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

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
                    intent = Intent(this, ContactlessPayActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        viewModel.userData.observe(this) { userData ->
            val recyclerView: RecyclerView = findViewById(R.id.cardContaclesRV)
            val adapter = DigitalCardContactlessAdapter(userData.cards ?: emptyList(),this)
            recyclerView.adapter= adapter
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = layoutManager         }
    }

    override fun onCardClick(selectedItem: DigitalCard) {
        selectedDigitalCard = selectedItem
    }
}