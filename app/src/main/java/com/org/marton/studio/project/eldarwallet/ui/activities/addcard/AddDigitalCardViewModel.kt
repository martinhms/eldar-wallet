package com.org.marton.studio.project.eldarwallet.ui.activities.addcard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.model.Bank
import com.org.marton.studio.project.eldarwallet.domain.model.CardBrand
import com.org.marton.studio.project.eldarwallet.domain.usecase.AddDigitalCardUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.CardUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDigitalCardViewModel @Inject constructor(
    private val addDigitalCardActivity: AddDigitalCardUseCase
) : ViewModel() {

    private val _cardTypeCode = MutableLiveData<Int>()
    val cardTypeCode: LiveData<Int> = _cardTypeCode

    private val _bankCode = MutableLiveData<Int>()
    val bankCode: LiveData<Int> = _bankCode

    @RequiresApi(Build.VERSION_CODES.P)
    fun addDigitalCard(number: Long, bankName: String, securityCode: Int, expirationDate: Long) {
        val digitalCard = DigitalCard(
            number,
            1234,
            _bankCode.value!!.toString(),
            CardUtils.getBrandCard(number)?.code ?: 0,
            _cardTypeCode.value!!,
            securityCode,
            expirationDate
        )
        viewModelScope.launch(Dispatchers.IO) {
            addDigitalCardActivity.invoke(digitalCard)
        }
    }

    fun onCardTypeSelected(cardTypeCode: Int) {
        _cardTypeCode.value = cardTypeCode
    }

    fun onBankSelected(bankSelected: Int) {
        _bankCode.value = bankSelected
    }

    fun formatCardNumber(cardNumber: String): String {
        val cleanedNumber = cardNumber.replace(" ", "")
        val formattedNumber = StringBuilder()
        for (i in cleanedNumber.indices) {
            if (i > 0 && i % 4 == 0) {
                formattedNumber.append(" ")
            }
            formattedNumber.append(cleanedNumber[i])
        }
        return formattedNumber.toString()
    }
}