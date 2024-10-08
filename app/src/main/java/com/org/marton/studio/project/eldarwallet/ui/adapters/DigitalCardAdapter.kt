package com.org.marton.studio.project.eldarwallet.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.ui.activities.OnCardClickListener
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.CardUtils

class DigitalCardAdapter(
    private val digitalCards: List<DigitalCard>,
    private val listener: OnCardClickListener? = null,
    private val layoutResource: Int,
    private val highlightSelected: Boolean = false
) : RecyclerView.Adapter<DigitalCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardBankNameTextView: TextView = itemView.findViewById(R.id.cardBankNameTextView)
        val cardNumberTextView: TextView = itemView.findViewById(R.id.cardNumberSelectedTextView)
        val cardManagmentNameTextView: TextView = itemView.findViewById(R.id.cardManagmentNameTextView)
        val cardTypeTextView: TextView = itemView.findViewById(R.id.cardTypeTextView)
    }

    private var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val digitalCard = digitalCards[position]

        if (highlightSelected && position == selectedItemPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.selected_card_color))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.default_card_color))
        }

        holder.cardBankNameTextView.text = CardUtils.getBankNameByCode(digitalCard.bank.toInt())
        holder.cardNumberTextView.text = CardUtils.formatCardNumber(digitalCard.number.toLong())
        holder.cardManagmentNameTextView.text = CardUtils.getBrandCardNameByCode(digitalCard.brand)
        holder.cardTypeTextView.text = CardUtils.getTypeCardDescByCode(digitalCard.type)

        if (listener != null) {
            holder.itemView.setOnClickListener {
                selectedItemPosition = position
                listener.onCardClick(digitalCard)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return digitalCards.size
    }
}