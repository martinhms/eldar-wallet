package com.org.marton.studio.project.eldarwallet.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.org.marton.studio.project.eldarwallet.data.db.DataBase
import com.org.marton.studio.project.eldarwallet.data.db.DigitalCardDao
import com.org.marton.studio.project.eldarwallet.data.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideUserDao(dataBase: DataBase): UserDao {
        return dataBase.userDao()
    }

    @Provides
    fun provideDigitalCardDao(dataBase: DataBase): DigitalCardDao {
        return dataBase.digitalCardDao()
    }

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext applicationContext: Context,
    ): DataBase {
        return Room.databaseBuilder(applicationContext, DataBase::class.java, "DataBase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(application: Application):
            SharedPreferences {
        val masterKey = MasterKey.Builder(application)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            application,
            "user_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}