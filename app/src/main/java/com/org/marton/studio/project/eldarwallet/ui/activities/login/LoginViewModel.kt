package com.org.marton.studio.project.eldarwallet.ui.activities.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.CreateUserDataUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val createUserDataUseCase: CreateUserDataUseCase) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.P)
    fun createUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            createUserDataUseCase(userData)
        }
    }
}