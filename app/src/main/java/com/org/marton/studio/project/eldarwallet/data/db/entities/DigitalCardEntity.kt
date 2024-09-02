package com.org.marton.studio.project.eldarwallet.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DigitalCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val number: Long,
    val ownerClientId: Long,
    val bankname: String,
    val managmentName: String,
    val type: String,
    val securityCode: Int,
    val expirationDate: Long
)