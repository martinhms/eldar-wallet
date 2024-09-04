package com.org.marton.studio.project.eldarwallet.utils

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Singleton

@Singleton
object SessionData {
    private const val PREFS_NAME = "user_session"
    private lateinit var sharedPrefs: SharedPreferences

    private val _userId = MutableStateFlow<Long>(0)
    val userId: StateFlow<Long> = _userId.asStateFlow()

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _userId.value = sharedPrefs.getLong("user_id", 0)
    }

    fun get(): Long {
        return userId.value
    }

    fun saveUserId(userId: Long) {
        sharedPrefs.edit().putLong("user_id", userId).apply()
        _userId.value = userId
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPrefs.getLong("user_id", 0) > 0
    }

    fun logout() {
        sharedPrefs.edit().remove("user_id").apply()
        _userId.value = 0
    }
}