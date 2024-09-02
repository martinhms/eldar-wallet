package com.org.marton.studio.project.eldarwallet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.org.marton.studio.project.eldarwallet.data.db.entities.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}