package com.org.marton.studio.project.eldarwallet.ui.activities.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.domain.DigitalCard

class DigitalCardAdapter(private val digitalCards: List<DigitalCard>) :
    RecyclerView.Adapter<DigitalCardAdapter.ViewHolder>() {class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cardBankNameTextView: TextView = itemView.findViewById(R.id.cardBankNameTextView)
    val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberTextView)
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
        holder.cardBankNameTextView.text = digitalCard.bankname
        holder.cardNumberTextView.text = formatCardNumber(digitalCard.numero)
        holder.cardManagmentNameTextView.text = digitalCard.managmentName
        holder.cardTypeTextView.text = digitalCard.type
    }

    override fun getItemCount(): Int {
        return digitalCards.size
    }

    private fun formatCardNumber(number: Int): String {
        return "**** **** **** ${number % 10000}"
    }
}