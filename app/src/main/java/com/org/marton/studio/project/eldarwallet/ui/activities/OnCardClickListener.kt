package com.org.marton.studio.project.eldarwallet.ui.activities

import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard

interface OnCardClickListener {
    fun onCardClick(selectedItem: DigitalCard)
}