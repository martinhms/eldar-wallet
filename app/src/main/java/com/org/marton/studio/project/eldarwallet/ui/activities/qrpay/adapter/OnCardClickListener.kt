package com.org.marton.studio.project.eldarwallet.ui.activities.qrpay.adapter

import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard

interface OnCardClickListener {
    fun onCardClick(selectedItem: DigitalCard)
}