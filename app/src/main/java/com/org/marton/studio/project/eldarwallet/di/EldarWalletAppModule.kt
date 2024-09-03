package com.org.marton.studio.project.eldarwallet.di

import android.content.Context
import androidx.room.Room
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
}