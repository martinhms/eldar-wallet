package com.org.marton.studio.project.eldarwallet.ui.models

 data class UserData (
    val id: Int,
    val userNamer: String,
    val userLastName: String,
    val cards : List<DigitalCard>,
    val baalnce: Double
)