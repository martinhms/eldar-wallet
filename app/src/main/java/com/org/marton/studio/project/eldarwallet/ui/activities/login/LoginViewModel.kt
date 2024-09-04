package com.org.marton.studio.project.eldarwallet.ui.activities.login

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.CreateUserDataUseCase
import com.org.marton.studio.project.eldarwallet.domain.usecase.SingInUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val singInUseCase: SingInUseCase,
    private val createUserDataUseCase: CreateUserDataUseCase,
) : ViewModel() {

    private val _isSignUp = MutableLiveData(false)
    val isSingnUp: LiveData<Boolean> = _isSignUp

    fun toggleSignUp() {
        _isSignUp.value = !(_isSignUp.value ?: false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getSingIn(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            singInUseCase(username, password).collect { userData ->
                SessionData.saveUserId(userData?.id ?: -1)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun createUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            createUserDataUseCase(userData)
        }
    }
}