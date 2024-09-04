package com.org.marton.studio.project.eldarwallet.ui

import android.app.Application
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.org.marton.studio.project.eldarwallet.utils.EncryptionHelper
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EldarWalletApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SessionData.init(this)
        AesGcmKeyManager.register(true)
        EncryptionHelper.init(this)
    }
}
