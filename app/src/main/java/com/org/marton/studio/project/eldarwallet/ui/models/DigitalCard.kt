package com.org.marton.studio.project.eldarwallet.ui.models

data class DigitalCard(
    val number: Long,
    val ownerClientId: Long,
    val bankname: String,
    val managmentName: String,
    val type: String,
    val securityCode: Int,
    val expirationDate: Long
)
