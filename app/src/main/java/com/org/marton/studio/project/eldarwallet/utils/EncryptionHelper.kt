package com.org.marton.studio.project.eldarwallet.utils

import android.content.Context
import android.util.Log
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadFactory
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import org.apache.commons.codec.binary.Base64
import java.nio.ByteBuffer
import java.security.GeneralSecurityException

object EncryptionHelper {
    private lateinit var aead: Aead

    fun init(context: Context) {
        AndroidKeysetManager.Builder()
            .withSharedPref(context, "my_keyset", "my_pref")
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri("android-keystore://my_master_key")
            .build()
            .keysetHandle
            .let {
                aead = AeadFactory.getPrimitive(it)
            }
    }

    fun encryptData(plaintext: Long): String {
        val byteArray = ByteBuffer.allocate(Long.SIZE_BYTES).putLong(plaintext).array()
        val encryptedByteArray = aead.encrypt(byteArray, null)
        return Base64.encodeBase64String(encryptedByteArray)
    }

    fun decryptData(ciphertext: String): Long {
        val encryptedByteArray = Base64.decodeBase64(ciphertext)
        val byteArray = aead.decrypt(encryptedByteArray, null)
        return ByteBuffer.wrap(byteArray).long
    }
}