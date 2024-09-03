package com.org.marton.studio.project.eldarwallet.domain.model

enum class CardType (val code: Int, val desc: String) {
    DEBIT(1, "Debit"),
    CREDIT(2, "Credit"),
}