package com.org.marton.studio.project.eldarwallet.ui.fragments.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.OnCardClickListener
import com.org.marton.studio.project.eldarwallet.ui.activities.addcard.AddDigitalCardActivity
import com.org.marton.studio.project.eldarwallet.ui.adapters.DigitalCardAdapter
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), OnCardClickListener {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addCardButton: FloatingActionButton = view.findViewById(R.id.agregarTarjetaButton)
        val balanceTextView: TextView = view.findViewById(R.id.balanceTextView)
        val usernameTextView: TextView = view.findViewById(R.id.usernameEditText)

        val recyclerView: RecyclerView = view.findViewById(R.id.tarjetasRecyclerView)
        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            usernameTextView.text = getGreattings(userData.userName + " " + userData.userLastname)
            balanceTextView.text = String.format("$ %.2f", userData.balance)

            val adapter = DigitalCardAdapter(
                userData.cards ?: emptyList(),
                null,
                R.layout.item_digital_card,
                false
            )
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        addCardButton.setOnClickListener {
            val intent = Intent(requireContext(), AddDigitalCardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getGreattings(s: String): String {
        return "Hi $s!"
    }

    override fun onCardClick(selectedItem: DigitalCard) {
        TODO("Not yet implemented")
    }
}
