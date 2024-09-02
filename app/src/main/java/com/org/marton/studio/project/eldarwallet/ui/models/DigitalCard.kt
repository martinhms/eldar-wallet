package com.org.marton.studio.project.eldarwallet.ui.models

data class DigitalCard(
    val numero: Int,
    val ownerClientId: Int,
    val bankname: String,
    val managmentName: String,
    val type: String,
    val securityCode: Int,
    val expirationDate: Int
)
