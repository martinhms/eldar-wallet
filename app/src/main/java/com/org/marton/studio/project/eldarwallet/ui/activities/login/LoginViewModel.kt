package com.org.marton.studio.project.eldarwallet.ui.activities.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.CreateUserDataUseCase
import com.org.marton.studio.project.eldarwallet.domain.usecase.GetUserDataUseCase
import com.org.marton.studio.project.eldarwallet.domain.usecase.IsDBEmptyUseCase
import com.org.marton.studio.project.eldarwallet.domain.usecase.SingInUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val singInUseCase: SingInUseCase,
    private val createUserDataUseCase: CreateUserDataUseCase,
    private val isDBEmptyUseCase: IsDBEmptyUseCase
) : ViewModel() {

    private val _isSignUp = MutableLiveData(false)
    val isSingnUp: LiveData<Boolean> = _isSignUp

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    fun toggleSignUp() {
        _isSignUp.value = !(_isSignUp.value ?: false)
    }

    init {
        if (isDBEmpty()) {
            createTestUsers()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun isDBEmpty(): Boolean {
        var isEmpty = false
        runBlocking(Dispatchers.IO) {
            isDBEmptyUseCase().firstOrNull()?.let { isEmpty = it == 0 }
        }
        return isEmpty
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getSingIn(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            singInUseCase(username, password).collect { userData ->
                val loginSuccessful = userData != null
                _loginState.postValue(loginSuccessful)
                if (loginSuccessful) {
                    SessionData.saveUserId(userData?.id ?: -1)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun createUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            createUserDataUseCase(userData)
        }
    }

    private fun createTestUsers() {
        createUser(UserData(
            userName = "user1",
            userLastname = "test 1",
            password = "1234",
            identification = "12345678",
            email = "testuser1@gmail.com",
        ))
        createUser(UserData(
            userName = "user2",
            userLastname = "test 2",
            password = "1234",
            identification = "12345679",
            email = "testuser2@gmail.com",
        ))
    }
}