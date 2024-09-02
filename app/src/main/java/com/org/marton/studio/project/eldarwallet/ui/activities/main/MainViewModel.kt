package com.org.marton.studio.project.eldarwallet.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.ui.models.UserData

class MainViewModel : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    init {
        getUserData()
        getDigitalCards()
        getBalance()
    }

    private fun getUserData() {
        //MOCK DATA
        val digitalCards = getDigitalCards()
        val balance = getBalance()
        val userData = UserData(1, "Martín", "Mendez", digitalCards, balance)
        _userData.value = userData
    }

    private fun getDigitalCards() = initCardList()

    private fun getBalance() = 1500.0

}

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