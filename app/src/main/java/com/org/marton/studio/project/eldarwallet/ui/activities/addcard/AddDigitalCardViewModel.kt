package com.org.marton.studio.project.eldarwallet.ui.activities.addcard

import android.os.Build
import android.text.Editable
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
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDigitalCardViewModel @Inject constructor(
    private val addDigitalCardActivity: AddDigitalCardUseCase
) : ViewModel() {

    private val cardTypeCode = MutableLiveData<Int>()
    private val bankCode = MutableLiveData<Int>()

    private val _expirationDate = MutableLiveData<String>()
    val expirationDate: LiveData<String> = _expirationDate

    @RequiresApi(Build.VERSION_CODES.P)
    fun addDigitalCard(number: Long, securityCode: Int, expirationDate: String) {
        val digitalCard = DigitalCard(
            number = number.toString(),
            ownerClientId = SessionData.get(),
            bank = bankCode.value!!.toString(),
            brand = CardUtils.getBrandCard(number)?.code ?: 0,
            type = cardTypeCode.value!!,
            securityCode = securityCode.toString(),
            expirationDate = expirationDate
        )
        viewModelScope.launch(Dispatchers.IO) {
            addDigitalCardActivity.invoke(digitalCard)
        }
    }

    fun onCardTypeSelected(typeCode: Int) {
        cardTypeCode.value = typeCode
    }

    fun onBankSelected(bankSelected: Int) {
        bankCode.value = bankSelected
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

    fun formatExpirationDate(editable: Editable) {
        val text = editable.toString().replace("/", "")
        if (text.length >= 2) {
            val formattedText = "${text.substring(0, 2)}/${text.substring(2)}"
            _expirationDate.value = formattedText
        } else {
            _expirationDate.value = text
        }
    }
}