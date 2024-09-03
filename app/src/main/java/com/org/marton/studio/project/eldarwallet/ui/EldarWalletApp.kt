package com.org.marton.studio.project.eldarwallet.ui

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EldarWalletApp : Application() {

    lateinit var sharedPrefs: EncryptedSharedPreferences

    override fun onCreate() {
        super.onCreate()

        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sharedPrefs = EncryptedSharedPreferences.create(
            "user_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }
}
