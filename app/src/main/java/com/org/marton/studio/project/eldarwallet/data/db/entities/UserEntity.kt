package com.org.marton.studio.project.eldarwallet.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val userName: String,
    val userLastname: String,
    val identification: String,
    val email: String,
    val avatar: String,
    val balance: Double,
    val createdTime: Long,
    val updatedTime: Long?,
    val deletedTime: Long?,
)
