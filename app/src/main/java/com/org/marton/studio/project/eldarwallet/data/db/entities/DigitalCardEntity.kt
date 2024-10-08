package com.org.marton.studio.project.eldarwallet.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DigitalCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val number: String,
    val ownerClientId: Long,
    val bank: String,
    val brand: Int,
    val type: Int,
    val securityCode: String,
    val expirationDate: String
)