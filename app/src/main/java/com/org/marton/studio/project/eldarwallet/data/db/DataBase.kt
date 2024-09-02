package com.org.marton.studio.project.eldarwallet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.org.marton.studio.project.eldarwallet.data.db.entities.DigitalCardEntity
import com.org.marton.studio.project.eldarwallet.data.db.entities.UserEntity

@Database(entities = [UserEntity::class, DigitalCardEntity::class], version = 4, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun digitalCardDao(): DigitalCardDao
}