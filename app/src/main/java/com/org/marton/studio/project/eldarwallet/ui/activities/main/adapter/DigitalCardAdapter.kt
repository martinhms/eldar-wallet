package com.org.marton.studio.project.eldarwallet.ui.activities.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.CardUtils

class DigitalCardAdapter(private val digitalCards: List<DigitalCard>) :
    RecyclerView.Adapter<DigitalCardAdapter.ViewHolder>() {class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cardBankNameTextView: TextView = itemView.findViewById(R.id.cardBankNameTextView)
    val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberSelectedTextView)
    val cardManagmentNameTextView: TextView = itemView.findViewById(R.id.cardManagmentNameTextView)
    val cardTypeTextView: TextView = itemView.findViewById(R.id.cardTypeTextView)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_digital_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val digitalCard = digitalCards[position]
        holder.cardBankNameTextView.text = CardUtils.getBankNameByCode(digitalCard.bank.toInt())
        holder.cardNumberTextView.text = CardUtils.formatCardNumber(digitalCard.number.toLong())
        holder.cardManagmentNameTextView.text =CardUtils.getBrandCardNameByCode(digitalCard.brand)
        holder.cardTypeTextView.text =CardUtils.getTypeCardDescByCode(digitalCard.type)
    }

    override fun getItemCount(): Int {
        return digitalCards.size
    }
}