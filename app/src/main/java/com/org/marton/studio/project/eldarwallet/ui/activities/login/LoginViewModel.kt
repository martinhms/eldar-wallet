package com.org.marton.studio.project.eldarwallet.ui.activities.login

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.CreateUserDataUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val createUserDataUseCase: CreateUserDataUseCase,
    private val sharedPrefs: SharedPreferences
) : ViewModel() {

    private val _userId = MutableLiveData<Long?>()
    val userId: LiveData<Long?> = _userId

    init {
        _userId.value = sharedPrefs.getLong("user_id", 0)
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
        _userId.value = null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun createUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            createUserDataUseCase(userData)
        }
    }
}