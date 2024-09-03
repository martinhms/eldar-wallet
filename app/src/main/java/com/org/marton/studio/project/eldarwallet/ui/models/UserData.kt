package com.org.marton.studio.project.eldarwallet.ui.models


data class UserData(
    val id: Long,
    val userName: String,
    val userLastname: String,
    val password: String,
    val identification: String,
    val email: String,
    val avatar: String? = "",
    val cards: List<DigitalCard>? = emptyList(),
    val balance: Double = 0.0,
    val createdTime: Long,
    val updatedTime: Long? = null,
    val deletedTime: Long? = null,
)