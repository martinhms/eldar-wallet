package com.org.marton.studio.project.eldarwallet.ui.activities.addcard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.AddDigitalCardUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDigitalCardViewModel @Inject constructor(
    private val addDigitalCardActivity: AddDigitalCardUseCase
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.P)
    fun addDigitalCard(number: Long, bankName: String, securityCode: Int, expirationDate: Long) {
        val digitalCard = DigitalCard(
            number,
            1234,
            bankName,
            "VISA",
            "Débito",
            securityCode,
            expirationDate
        )
        viewModelScope.launch(Dispatchers.IO) {
            addDigitalCardActivity.invoke(digitalCard)
        }
    }
}