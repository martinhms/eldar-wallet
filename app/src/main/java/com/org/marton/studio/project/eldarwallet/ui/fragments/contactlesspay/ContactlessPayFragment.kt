package com.org.marton.studio.project.eldarwallet.ui.fragments.contactlesspay

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.OnCardClickListener
import com.org.marton.studio.project.eldarwallet.ui.adapters.DigitalCardAdapter
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.CardUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactlessPayFragment : Fragment(), OnCardClickListener {

    private val viewModel: ContactlessPayViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contactless_pay, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardBrandTextView: TextView = view.findViewById(R.id.cardBrandSelectedView)
        val cardTypeTextView: TextView = view.findViewById(R.id.cardTypeSelectedImageView)
        val cardNumberTextView: TextView = view.findViewById(R.id.cardNumberSelectedTextView)
        val recyclerView: RecyclerView = view.findViewById(R.id.cardContaclesRV)

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            val adapter = DigitalCardAdapter(
                userData.cards ?: emptyList(),
                this,
                R.layout.item_digital_card_contactless,
                true
            )
            recyclerView.adapter = adapter
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = layoutManager
        }

        viewModel.selectedCard.observe(viewLifecycleOwner) { selectedCard ->
            cardBrandTextView.text = CardUtils.getBrandCardNameByCode(selectedCard.brand)
            cardTypeTextView.text = CardUtils.getTypeCardDescByCode(selectedCard.type)
            cardNumberTextView.text = CardUtils.formatCardNumber(selectedCard.number.toLong())
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCardClick(selectedItem: DigitalCard) {
        viewModel.setSelectedCard(selectedItem)
    }

}
