package com.org.marton.studio.project.eldarwallet.ui.activities.qrpay

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
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
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.contactlesspay.ContactlessPayActivity
import com.org.marton.studio.project.eldarwallet.ui.activities.main.MainActivity
import com.org.marton.studio.project.eldarwallet.ui.activities.qrpay.adapter.DigitalCardQrAdapter
import com.org.marton.studio.project.eldarwallet.ui.activities.OnCardClickListener
import com.org.marton.studio.project.eldarwallet.ui.activities.login.LoginActivity
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrPayActivity : AppCompatActivity(), OnCardClickListener {

    private val viewModel: QrPayViewModel by viewModels()
    private var selectedCardNumber: Long? = null

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

        val toolbar = findViewById<Toolbar>(R.id.toolbarQR)
        setSupportActionBar(toolbar)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = MainActivity.selectedItemId

        val bottomGenerateQr: Button = findViewById(R.id.generatePaymentButton)
        viewModel.userData.observe(this) { userData ->
            val recyclerView: RecyclerView = findViewById(R.id.selectCardsPayRV)
            val adapter = DigitalCardQrAdapter(userData.cards ?: emptyList(), this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        val imageView: ImageView = findViewById(R.id.qrCodeImageView)
        viewModel.qrCodeBitmap.observe(this) { bitmap ->
            imageView.setImageBitmap(bitmap)
        }

        viewModel.isQeGenerated.observe(this) {
            if (!it) {
                imageView.visibility = View.INVISIBLE
            } else {
                imageView.visibility = View.VISIBLE
            }
        }
        viewModel.errorQr.observe(this) { errorMessage ->
            errorMessage?.let {
                if (it.isNotEmpty()) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        bottomGenerateQr.setOnClickListener {
            val qrData = viewModel.getUserName() + ":" + (selectedCardNumber?.toString() ?: "")
            viewModel.generateQrCode(qrData)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            MainActivity.selectedItemId = item.itemId
            when (item.itemId) {
                R.id.qr_paid_tab -> {
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
    }

    override fun onCardClick(selectedItem: DigitalCard) {
        selectedCardNumber = selectedItem.number.toLong()
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
}