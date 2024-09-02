package com.org.marton.studio.project.eldarwallet.ui.activities.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.GetUserDataUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    init {
        getUserData("1")
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserDataUseCase(userId).collect { userData ->
                val digitalCards = getDigitalCards()
                val balance = getBalance()
                _userData.postValue(
                    UserData(
                        id = userData.id,
                        userName = userData.userName,
                        userLastname = userData.userLastname,
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

private fun getDigitalCards() = initCardList()

private fun getBalance() = 1500.0

fun initCardList() = listOf(
    DigitalCard(
        1234567890,
        1,
        "Banco Galicia",
        "Visa",
        "Crédito",
        123,
        202412
    ),
    DigitalCard(
        1234567820,
        1,
        "Banco Ciudad",
        "Master Card",
        "Crédito",
        123,
        202412
    ),
    DigitalCard(
        1234563890,
        1,
        "Banco Macro",
        "Visa",
        "Crédito",
        123,
        202412
    ),
    DigitalCard(
        1234563890,
        1,
        "Banco Macro",
        "Visa",
        "Crédito",
        123,
        202412
    ),
    DigitalCard(
        1234563890,
        1,
        "Banco Macro",
        "Visa",
        "Crédito",
        123,
        202412
    ),
    DigitalCard(
        1234563890,
        1,
        "Banco Macro",
        "Visa",
        "Crédito",
        123,
        202412
    ),
    DigitalCard(
        1234563890,
        1,
        "Banco Macro",
        "Visa",
        "Crédito",
        123,
        202412
    ),
    DigitalCard(
        987654321,
        1,
        "Banco Santander",
        "Mastercard",
        "Débito",
        456,
        202506
    )

)