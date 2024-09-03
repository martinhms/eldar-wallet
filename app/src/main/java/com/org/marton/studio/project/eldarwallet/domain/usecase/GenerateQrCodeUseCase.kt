package com.org.marton.studio.project.eldarwallet.domain.usecase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.org.marton.studio.project.eldarwallet.data.network.QRCodeApi
import javax.inject.Inject

class GenerateQrCodeUseCase @Inject constructor(
    private val qrCodeApi: QRCodeApi
) {
    suspend operator fun invoke(data: String): Result<Bitmap> {
        return try {
            val response = qrCodeApi.generateQrCode(data).execute()
            if (response.isSuccessful) {
                val bytes = response.body()?.bytes()
                val bitmap = if (bytes != null) BitmapFactory.decodeByteArray(bytes, 0, bytes.size) else null
                Log.d("GenerateQrCodeUseCase", "QR Code generated ok")
                Result.success(bitmap ?: throw Exception("Error in decoding QR Code"))
            } else {
                Log.e("GenerateQrCodeUseCase", "Error al generar el código QR: ${response.errorBody()?.string()}")
                Result.failure(Exception("Error al generating QR: ${response}"))
            }
        } catch (e: Exception) {
            Log.e("GenerateQrCodeUseCase", "Error en generateQrCodeUseCase: ${e.message}")
            Result.failure(e)
        }
    }
}
