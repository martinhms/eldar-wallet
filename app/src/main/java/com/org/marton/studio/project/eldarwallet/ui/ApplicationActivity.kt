package com.org.marton.studio.project.eldarwallet.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.login.LoginActivity
import com.org.marton.studio.project.eldarwallet.ui.fragments.contactlesspay.ContactlessPayFragment
import com.org.marton.studio.project.eldarwallet.ui.fragments.main.MainFragment
import com.org.marton.studio.project.eldarwallet.ui.fragments.qrpay.QrPayFragment
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationActivity : AppCompatActivity() {

    companion object {
        var selectedItemId = R.id.main_activity_tab
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_application)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            selectedItemId = item.itemId
            when (item.itemId) {
                R.id.qr_paid_tab -> {
                    supportFragmentManager.commit {
                        replace<QrPayFragment>(R.id.frameApplicationContainer)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }
                    true
                }

                R.id.main_activity_tab -> {
                    supportFragmentManager.commit {
                        replace<MainFragment>(R.id.frameApplicationContainer)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }
                    true
                }

                R.id.contacless_paid_tab -> {
                    supportFragmentManager.commit {
                        replace<ContactlessPayFragment>(R.id.frameApplicationContainer)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }
                    true
                }

                else -> false
            }
        }
        bottomNavigationView.selectedItemId = selectedItemId
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