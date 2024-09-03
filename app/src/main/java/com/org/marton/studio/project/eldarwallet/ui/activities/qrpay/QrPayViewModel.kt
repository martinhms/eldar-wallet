package com.org.marton.studio.project.eldarwallet.ui.activities.qrpay

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.GetUserDataUseCase
import com.org.marton.studio.project.eldarwallet.domain.usecase.GetUserDigitalCardsUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class QrPayViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserDigitalCardsUseCase: GetUserDigitalCardsUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    init {
        getUserData("1234")
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserDataUseCase(userId).collect { userData ->
                getUserDigitalCardsUseCase(userId).collect { digitalCards ->
                    val balance = getBalance()
                    _userData.postValue(
                        UserData(
                            id = userData.id,
                            userName = userData.userName,
                            userLastname = userData.userLastname,
                            password = userData.password,
                            cards = digitalCards,
                            balance = balance,
                            identification = userData.identification,
                            email = userData.email,
                            avatar = userData.avatar,
                            createdTime = userData.createdTime,
                            updatedTime = userData.updatedTime,
                        )
                    )
                }
            }
        }
    }

    private fun getBalance() = 1500.0
}