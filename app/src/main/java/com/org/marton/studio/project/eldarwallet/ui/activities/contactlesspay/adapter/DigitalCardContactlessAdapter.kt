package com.org.marton.studio.project.eldarwallet.ui.activities.contactlesspay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.qrpay.adapter.OnCardClickListener
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.CardUtils

class DigitalCardContactlessAdapter(
    private val digitalCards: List<DigitalCard>,
    private val listener: OnCardClickListener
) :
    RecyclerView.Adapter<DigitalCardContactlessAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardBankNameTextView: TextView = itemView.findViewById(R.id.cardBankNameTextView)
        val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberTextView)
        val cardManagmentNameTextView: TextView =
            itemView.findViewById(R.id.cardManagmentNameTextView)
        val cardTypeTextView: TextView = itemView.findViewById(R.id.cardTypeTextView)
    }

    private var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_digital_card_contactless, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val digitalCard = digitalCards[position]
        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.selected_card_color)) // Cambia el color de fondo
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.default_card_color)) // Restaura el color de fondo
        }
        holder.cardBankNameTextView.text = CardUtils.getBankNameByCode(digitalCard.bank.toInt())
        holder.cardNumberTextView.text = formatCardNumber(digitalCard.number)
        holder.cardManagmentNameTextView.text = CardUtils.getBrandCardNameByCode(digitalCard.brand)
        holder.cardTypeTextView.text = CardUtils.getTypeCardDescByCode(digitalCard.type)
        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            listener.onCardClick(digitalCard)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return digitalCards.size
    }

    private fun formatCardNumber(number: Long): String {
        return "**** **** **** ${number % 10000}"
    }
}