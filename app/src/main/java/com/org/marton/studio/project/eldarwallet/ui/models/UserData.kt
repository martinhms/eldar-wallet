package com.org.marton.studio.project.eldarwallet.ui.models

 data class UserData (
    val id: Int,
    val userName: String,
    val userLastname: String,
    val cards : List<DigitalCard>,
    val balance: Double
)