package com.org.marton.studio.project.eldarwallet.ui.models

data class DigitalCard(
    val number: String,
    val ownerClientId: Long,
    val bank: String,
    val brand: Int,
    val type: Int,
    val securityCode: String,
    val expirationDate: Long
)
