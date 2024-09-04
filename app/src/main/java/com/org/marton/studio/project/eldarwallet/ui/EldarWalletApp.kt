package com.org.marton.studio.project.eldarwallet.ui

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EldarWalletApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SessionData.init(this)

    }
}
