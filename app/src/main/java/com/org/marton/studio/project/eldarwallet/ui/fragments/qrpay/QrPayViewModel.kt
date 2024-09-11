package com.org.marton.studio.project.eldarwallet.ui.fragments.qrpay

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.marton.studio.project.eldarwallet.domain.usecase.GenerateQrCodeUseCase
import com.org.marton.studio.project.eldarwallet.domain.usecase.GetUserDataUseCase
import com.org.marton.studio.project.eldarwallet.domain.usecase.GetUserDigitalCardsUseCase
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import com.org.marton.studio.project.eldarwallet.utils.SessionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class QrPayViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserDigitalCardsUseCase: GetUserDigitalCardsUseCase,
    private val generateQrCodeUseCase: GenerateQrCodeUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    private val _qrCodeBitmap = MutableLiveData<Bitmap?>()
    val qrCodeBitmap: LiveData<Bitmap?> = _qrCodeBitmap
    private val _errorQr = MutableLiveData("")
    val errorQr: LiveData<String> = _errorQr
    private val _isQeGenerated= MutableLiveData(false)
    val isQeGenerated: LiveData<Boolean> = _isQeGenerated

    init {
        getUserData(SessionData.get().toString())
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserDataUseCase(userId).collect { userData ->
                getUserDigitalCardsUseCase(userId).collect { digitalCards ->
                    _userData.postValue(
                        UserData(
                            id = userData.id,
                            userName = userData.userName,
                            userLastname = userData.userLastname,
                            password = userData.password,
                            cards = digitalCards,
                            balance = userData.balance,
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

    fun generateQrCode(data: String) {
        viewModelScope.launch(Dispatchers.IO) {
            generateQrCodeUseCase(data).onSuccess { bitmap ->
                _qrCodeBitmap.postValue(bitmap)
                _errorQr.postValue("")
                _isQeGenerated.postValue(true)
            }.onFailure {
                _qrCodeBitmap.postValue(null)
                _isQeGenerated.postValue(false)
                _errorQr.postValue("Error generating QR code")
            }
        }
    }

     fun getUserName() = userData.value?.userName + " " + userData.value?.userLastname
}