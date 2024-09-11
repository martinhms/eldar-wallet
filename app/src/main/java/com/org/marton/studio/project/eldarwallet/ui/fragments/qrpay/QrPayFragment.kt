package com.org.marton.studio.project.eldarwallet.ui.fragments.qrpay

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.OnCardClickListener
import com.org.marton.studio.project.eldarwallet.ui.activities.login.LoginActivity
import com.org.marton.studio.project.eldarwallet.ui.adapters.DigitalCardQrAdapter
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrPayFragment : Fragment(), OnCardClickListener {

    private val viewModel: QrPayViewModel by viewModels()
    private var selectedCardNumber: Long? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_pay, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomGenerateQr: Button = view.findViewById(R.id.generatePaymentButton)
        val recyclerView: RecyclerView = view.findViewById(R.id.selectCardsPayRV)
        val imageView: ImageView = view.findViewById(R.id.qrCodeImageView)

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            val adapter = DigitalCardQrAdapter(userData.cards ?: emptyList(), this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.qrCodeBitmap.observe(viewLifecycleOwner) { bitmap ->
            imageView.setImageBitmap(bitmap)
        }

        viewModel.isQeGenerated.observe(viewLifecycleOwner) {
            if (!it) {
                imageView.visibility = View.INVISIBLE
            } else {
                imageView.visibility = View.VISIBLE
            }
        }

        viewModel.errorQr.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        bottomGenerateQr.setOnClickListener {
            val qrData = viewModel.getUserName() + ":" + (selectedCardNumber?.toString() ?: "")
            viewModel.generateQrCode(qrData)
        }
    }

    override fun onCardClick(selectedItem: DigitalCard) {
        selectedCardNumber = selectedItem.number.toLong()
    }

    private fun logout() {
        SessionData.logout()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }
}